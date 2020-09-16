package com.searchengine.yjpark.api.service;

import com.searchengine.yjpark.admin.service.ServiceService;
import com.searchengine.yjpark.api.response.SearchResultResponse;
import com.searchengine.yjpark.domain.DataBaseInfo;
import com.searchengine.yjpark.domain.Indexing;
import com.searchengine.yjpark.domain.Search;
import com.searchengine.yjpark.es.client.ElasticsearchClient;
import com.searchengine.yjpark.repository.BulkRepository;
import com.searchengine.yjpark.repository.ServiceRepository;
import org.apache.lucene.search.TotalHits;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class IndexService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ElasticsearchClient elasticsearchClient;

    @Autowired
    ServiceRepository serviceRepository;

    @Autowired
    BulkRepository bulkRepository;

    @Autowired
    ServiceService serviceService;

    /**
     * 전체 색인(Bulk) 서비스
     *
     * @param serviceId
     */
    public boolean bulkIndex(String serviceId) {
        // 1. serviceId와 일치하는 service BulkQuery 가져오기
        com.searchengine.yjpark.domain.Service serviceInfo = serviceService.findServiceByID(serviceId);
        String bulkQuery = serviceInfo.getBulkQuery();
        Long dbIdx = serviceInfo.getDbIdx(); // db 정보 가져오기 위해
        String docColumnId = serviceInfo.getIdColumn();

        List<DataBaseInfo> dbInfo = serviceService.findDbByIdx(dbIdx);

        // 2. DB 정보로 동적 연결 - bulk Model 호출
        int countRow = bulkRepository.countCuration(dbInfo, serviceInfo);

        // 3. MySQL 데이터 Json 형식 변환
        // https://stackoverflow.com/questions/58772409/convert-all-the-mysql-table-data-into-json-in-spring-boot
        List<Map<String, Object>> jsonData = bulkRepository.dynamicMapping(dbInfo, serviceInfo);
        // @Test Code
        // jsonData.forEach(x -> log.info("test Code : {}", x));

        String indexId = "my_" + serviceId; // prefix 설정

        // 4. Index 존재하는지 검사
        boolean result = elasticsearchClient.isIndexExist(indexId);
        log.info("기존 Index 가 있는지 : {}", result);
        log.info("총 색인 건수 : {}", countRow);
        // (4-1). 만약 있다면 Delete 함수 호출(삭제)
        if (result) {
            // Admin 기능에 모달창 띄우기 연동
            boolean isIndexExist = elasticsearchClient.IndexDelete(indexId);
            log.info(">> Success Index Delete : {}", isIndexExist);
        }

        // 5. ES Index 생성
        // https://www.elastic.co/guide/en/elasticsearch/client/java-rest/current/java-rest-high-document-index.html
        BulkRequest bulkRequest = new BulkRequest();
        // Json Data 변환
        jsonData.forEach(map -> {
            // log.info("map 전체 : {}", map.toString());
            IndexRequest indexRequest = new IndexRequest(indexId);
            // log.info("Primary Key : {}", map.get(docColumnId).toString());
            indexRequest.id(map.get(docColumnId).toString()); // Doc id 할당
            indexRequest.source(map); // Todo Template 과 Timestamp 불일치 오류 해결하기
            // elasticsearchClient.indexCreate(indexRequest);
            // 6. Bulk 하기
            bulkRequest.add(indexRequest);
        });
        // ES Client Bulk 실행
        // Todo 성공 실패 받기
        return elasticsearchClient.bulkInsert(bulkRequest);
    }

    /**
     * 검색 서비스 로직
     *
     * @param search
     * @return SearchResponse
     */
    public SearchResultResponse searchKeyword(Search search) {
        // Request Body {서비스 ID}, {검색 단어}, {하이라이팅 필드 지정}
        String serviceId = search.getServiceId();
        String searchText = search.getSearchText();
        Map<String, Object> columns = search.getHighlight();
        List<String> resultColumns = search.getResultColumns();

        log.info("columns Map : {}", columns);

        // 검색어 적용할 Column List
        List<String> columnNames = new ArrayList<>(resultColumns.size()); // 초기화는 결과 컬럼과 동일하게 크게 잡기
        // 하이라이팅 키워드
        String prefixTag = "";
        String postfixTag = "";
        // 페이징 - 사이즈랑 번호
        int pageSize = search.getPageSize();
        int pageNo = search.getPageNo();

        // Iterator 로 모든 Highlight 관련 값 가져오기
        Iterator<Map.Entry<String, Object>> entries = columns.entrySet().iterator();

        // Todo 나중에 for 문 줄이기
        while (entries.hasNext()) { // highlight 의 key, value 매핑
            Map.Entry<String, Object> entry = entries.next(); // Map.Entry 활용
            if (entry.getKey().equals("columns")) { // columns 필드라면
                List<Map<String, Object>> column = (List<Map<String, Object>>) entry.getValue();
                log.info("for column : {}", column);
                for (Map<String, Object> filed : column) {
                    // Search 할 필드 이름 넣기
                    // Todo 위 선언부터 Map.Entry로 바꿔보기
                    for (Map.Entry<String, Object> filedVal : filed.entrySet()) {
                        columnNames.add(filedVal.getValue().toString());
                    }
                }
            } else if (entry.getKey().equals("prefixTag")) { // prefix_tag, postfix_tag 받아오기
                prefixTag = entry.getValue().toString();
            } else {
                postfixTag = entry.getValue().toString();
            }
        }

        log.info("Search & Highlight field\n> columnNames : {}\n> prefixTag : {}\n> postfixTag : {}", columnNames, prefixTag, postfixTag);

        // 1. SourceBuilder (Search Query 만들기, 결과 source filter, 하이라이팅 빌더)
        // 1-1. 결과필드지정 query 만들어주는 Builder
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder(); // 검색 관련 @param 추가하는 builder
        // Todo MatchQuery 이슈 해결하기
        // 1-2. Match Qurty 적용
//        searchSourceBuilder1.query(QueryBuilders.multiMatchQuery(searchText, {동적Field lists})); // Todo 동적 lists 할당법
//        searchSourceBuilder.query(QueryBuilders.multiMatchQuery(searchText, columnNames.get(0), columnNames.get(1)));
//        searchSourceBuilder.query(QueryBuilders.boolQuery().must(QueryBuilders.matchQuery(searchText, resultColumns)));

        // 이렇게 하니 match Query 가 덮어쓰기 됨

        /*
        for (String result : resultColumns) {
            searchSourceBuilder.query(QueryBuilders.matchQuery(searchText, result));
        }

         */

        String[] str = new String[columnNames.size()];

        for(int i = 0; i< columnNames.size(); i++) {
            str[i] = columnNames.get(i);
        }

        searchSourceBuilder.query(QueryBuilders.multiMatchQuery(searchText, str));

        // 1-3. 하이라이트 query 만들어주는 Builder
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        // 1-4. 하이라이트할 field 지정하기
        // Todo For loop을 돌려서 MatchQuery로 field 하나씩 검색하는 방법 적용해보기
        for (String field : columnNames) {
            // Todo Test 돌려서 highlight 여러 개 되는지 확인하기
            HighlightBuilder.Field highlightText = new HighlightBuilder.Field(field);
            highlightText.highlighterType("unified");
            highlightBuilder.field(highlightText);
            highlightBuilder.preTags(prefixTag);
            highlightBuilder.postTags(postfixTag);
            searchSourceBuilder.highlighter(highlightBuilder); // Todo 추가안해도 filed 명 바뀌면서 추가되는지
        }
        // 1-5 pagination
        searchSourceBuilder.from(pageNo);
        searchSourceBuilder.size(pageSize);

        // 1-6. (만약 위에서 잘못됐으면) 하이라이트 생성
        // searchSourceBuilder.highlighter(highlightBuilder);

        // 1-7. 설정된 Builder ES 클라이언트에 넘겨주기

        // 6. 컨트롤러에 전달

        SearchResponse searchResponse = elasticsearchClient.search(serviceId, searchSourceBuilder);

        log.info("ES SearchResponse : {}", searchResponse);

        // 5. return 받은 Response API 정의대로 변환
        SearchResultResponse searchResultResponse = new SearchResultResponse();
        SearchHits hits = searchResponse.getHits();
        TotalHits totalHits = hits.getTotalHits();
        int numHits = (int) totalHits.value;
        // @TEST
        log.info("total hit : {}", numHits);

        // Todo Result 결과 필드 가져오는 법
        // Get Source API 에 있는 줄 알았는데 안됨
        // https://www.elastic.co/guide/en/elasticsearch/client/java-rest/7.9/java-rest-high-document-get-source.html

        SearchHits highlightHit = searchResponse.getHits();

        // SearchHit test = searchResponse.getHits().getAt(1).highlightFields();


        // 검색 결과 저장하는 for 문
        // resultList 안에 add 칠때 highlight가 key : value(
        List<Map<String, Object>> resultList = new ArrayList<>();
        for (SearchHit hit : searchResponse.getHits()) {
            log.info("highlight : {}", hit.getHighlightFields());
            Map<String, HighlightField> highlightField = hit.getHighlightFields();
            Map<String, Object> hl = new HashMap<>();
            highlightField.forEach((s, highlightField1) -> {
                        Text[] frag = highlightField1.getFragments();
                        hl.put(s, frag[0].toString());
                    }
            );
            Map<String, Object> jsonString = hit.getSourceAsMap();
            jsonString.put("highlight", hl);
//            log.info("JSON 어떻게 들어가? : {}", jsonString);
            resultList.add(jsonString);
            // resultList.add(hit.getHighlightFields());
        }

        searchResultResponse.setTotal(numHits);
        searchResultResponse.setPage(pageNo);
        searchResultResponse.setPageSize(pageSize);
        searchResultResponse.setResult(resultList);
        return searchResultResponse;
    }

    // 부분 색인 생성 - Create
    public void createDocument(Indexing indexing) {
        // 1. 서비스 ID로 ES 인덱스 접근
        String indexId = indexing.getServiceId(); // 인덱스 id
        String documentId = indexing.getContentsIdValue(); // document id
        Map<String, Object> jsonMap = new HashMap<>();
        // @Test data
        jsonMap.put("curation_seq", "300");
        jsonMap.put("curation_title", "테스트 document 생성했음");
        jsonMap.put("template", "5");
        jsonMap.put("template_img", null);
        jsonMap.put("ending_txt", "잘 생성됐는지 테스트");
        // 2. 이미 해당 번호 도큐먼트 존재하는지 여부 확인
        boolean isDocumentExist = elasticsearchClient.isDocumentExist(indexId, documentId);
        if(isDocumentExist) {
            log.error("This DocumentID [ {} ] is Already Exist : ", documentId);
        }

        // 3. 색인 생성(도큐먼트 한개)
        boolean result = elasticsearchClient.documentCreate(indexId, documentId, jsonMap);

        // 4. 색인 업데이트(도큐먼트 한개 업데이트)
        // 4.1 해당 번호 도큐먼트가 있는지(없으면 튕기기 or 새로 생성)
        // 4.2 업데이트하는 ES 클라이언트 호출
        // 5. 색인 삭제(도큐먼트 한개 삭제)
        // 5-1. 삭제할 도큐먼트가 있는지 (4-1 같은 맥락)
        // 5-2. 삭제하는 ES 클라이언트 호출
        // 색인 완료되면 @return true

        indexing.getServiceId();

    }

    // 부분 색인 업데이트 - Update
    public void updateDocument(Indexing indexing) {
        // 1. 서비스 ID로 ES 인덱스 접근
        String indexId = indexing.getServiceId(); // 인덱스 id
        String documentId = indexing.getContentsIdValue(); // document id
        Map<String, Object> jsonMap = new HashMap<>();
        // @Test data
        jsonMap.put("curation_seq", "300");
        jsonMap.put("curation_title", "테스트 document 생성했음");
        jsonMap.put("template", "5");
        jsonMap.put("template_img", null);
        jsonMap.put("ending_txt", "잘 생성됐는지 테스트");

    }

    // 부분 색인 삭제 - Delete
    public void deleteDocument(Indexing indexing) {

    }

    // 전체 Indices 가져오기
    public List<Map<String, Object>> getAllIndices() {
        String indicesList = elasticsearchClient.getIndexList();
        String[] temp_list = indicesList.split("\\s+");
        List<Map<String, Object>> indicesMap = new ArrayList<>();
        Map<String, Object> jsonMap = new HashMap<>();

        for(int i = 0; i < temp_list.length; i = i+10) {
            jsonMap.put("health", temp_list[i]);
            jsonMap.put("status", temp_list[i+1]);
            jsonMap.put("index", temp_list[i+2]);
            jsonMap.put("uuid", temp_list[i+3]);
            jsonMap.put("pri", temp_list[i+4]);
            jsonMap.put("rep", temp_list[i+5]);
            jsonMap.put("docs.count", temp_list[i+6]);
            jsonMap.put("docs.deleted", temp_list[i+7]);
            jsonMap.put("store.size", temp_list[i+8]);
            jsonMap.put("pri.store.size", temp_list[i+9]);
            indicesMap.add(jsonMap);
        }
        jsonMap.get("health");
        /*
        for(int i=0; i < temp_list.length; i++){
            log.info("splitList1 {} : {}", i, temp_list[i]);
        }
        */

        // log.info("indicesList : {}", indicesList);
        return indicesMap;
    }

    public void testIsIndexExist(){
        String indexName = "book";
        boolean result = elasticsearchClient.isIndexExist(indexName);
        log.info("client test : {}", result);
    }

}
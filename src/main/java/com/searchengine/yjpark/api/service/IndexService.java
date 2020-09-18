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

import java.text.SimpleDateFormat;
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
     * @return
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
        List<Map<String, Object>> jsonData = bulkRepository.dynamicMapping(dbInfo, serviceInfo);

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

        // 5. ES Index 생성 (bulk Request)
        BulkRequest bulkRequest = new BulkRequest();
        // Json Data 변환
        jsonData.forEach(map -> {
            IndexRequest indexRequest = new IndexRequest(indexId);
            indexRequest.id(map.get(docColumnId).toString()); // Doc id 할당
            indexRequest.source(map);
            bulkRequest.add(indexRequest);
        });
        // ES Client Bulk 실행
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

        // Map.Entry 활용
        for (Map.Entry<String, Object> entry : columns.entrySet()) { // highlight 의 key, value 매핑
            if (entry.getKey().equals("columns")) { // columns 필드라면
                List<Map<String, Object>> column = (List<Map<String, Object>>) entry.getValue();
                log.info("for column : {}", column);
                for (Map<String, Object> filed : column) {
                    // Search 할 필드 이름 넣기
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

        String[] str = new String[columnNames.size()];

        for(int i = 0; i< columnNames.size(); i++) {
            str[i] = columnNames.get(i);
        }

        searchSourceBuilder.query(QueryBuilders.multiMatchQuery(searchText, str));

        // 1-3. 하이라이트 query 만들어주는 Builder
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        // 1-4. 하이라이트할 field 지정하기
        for (String field : columnNames) {
            HighlightBuilder.Field highlightText = new HighlightBuilder.Field(field);
            highlightText.highlighterType("unified");
            highlightBuilder.field(highlightText);
            highlightBuilder.preTags(prefixTag);
            highlightBuilder.postTags(postfixTag);
            searchSourceBuilder.highlighter(highlightBuilder);
        }
        // 1-5 pagination
        searchSourceBuilder.from(pageNo);
        searchSourceBuilder.size(pageSize);

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

        SearchHits highlightHit = searchResponse.getHits();

        // 검색 결과 저장하는 for 문
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
            resultList.add(jsonString);
        }

        searchResultResponse.setTotal(numHits);
        searchResultResponse.setPage(pageNo);
        searchResultResponse.setPageSize(pageSize);
        searchResultResponse.setResult(resultList);
        return searchResultResponse;
    }

    // 부분 색인 생성 - Create
    public void createDocument(Indexing indexing) {

        List<String> columns = indexing.getColumns();
        log.info("columns : {}", columns);
        // 1. 서비스 ID로 ES 인덱스 접근
        String indexId = indexing.getServiceId(); // 인덱스 id
        String documentId = indexing.getContentsIdValue(); // document id
        Map<String, Object> jsonMap = new HashMap<>();
        // @Test data
        SimpleDateFormat format = new SimpleDateFormat ( "yyyy년 MM월dd일 HH시mm분ss초");
        Date time = new Date();
        String strTime = format.format(time);

        jsonMap.put("curation_seq", documentId);
        jsonMap.put("curation_title", "부분색인 POST - 테스트 document 생성!");
        jsonMap.put("template", "5");
        jsonMap.put("template_img", null);
        jsonMap.put("ending_txt", strTime);
        // 2. 이미 해당 번호 도큐먼트 존재하는지 여부 확인
        boolean isDocumentExist = elasticsearchClient.isDocumentExist(indexId, documentId);
        if (isDocumentExist) {
            log.error("This DocumentID [ {} ] is Already Exist : ", documentId);
        }

        // 3. 색인 생성(도큐먼트 한개)
        boolean createResult = elasticsearchClient.documentCreate(indexId, documentId, jsonMap);
        log.info("부분색인 - createResult : {}", createResult);
    }

    // 부분 색인 업데이트 - Update
    public void updateDocument(Indexing indexing) {
        // 1. 서비스 ID로 ES 인덱스 접근
        String indexId = indexing.getServiceId(); // 인덱스 id
        String documentId = indexing.getContentsIdValue(); // document id
        Map<String, Object> jsonMap = new HashMap<>();
        // @Test data
        SimpleDateFormat format = new SimpleDateFormat ( "yyyy년 MM월dd일 HH시mm분ss초");
        Date time = new Date();
        String strTime = format.format(time);
        jsonMap.put("curation_seq", documentId);
        jsonMap.put("curation_title", "부분색인 PUT - 기존 doc 업데이트");
        jsonMap.put("template", "5");
        jsonMap.put("template_img", null);
        jsonMap.put("ending_txt", strTime);

        // 2. 업데이트
        boolean updateResult = elasticsearchClient.documentUpdate(indexId, documentId, jsonMap);
        log.info("부분색인 - updateResult : {}", updateResult);
    }

    // 부분 색인 삭제 - Delete
    public void deleteDocument(Indexing indexing) {
        String indexId = indexing.getServiceId(); // 인덱스 id
        String documentId = indexing.getContentsIdValue(); // document id
        boolean deleteResult = elasticsearchClient.documentDelete(indexId, documentId);
        log.info("부분색인 - deleteResult : {}", deleteResult);
    }

    // 전체 Indices 가져오기
    public List<Map<String, Object>> getAllIndices() {
        String indicesList = elasticsearchClient.getIndexList();
        String[] temp_list = indicesList.split("\\s+");
        List<Map<String, Object>> indicesMap = new ArrayList<>();

        for (int i = 0; i < temp_list.length; i = i + 10) {
            if(!temp_list[i + 2].startsWith("my_")) continue;
            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("health", temp_list[i]);
            jsonMap.put("status", temp_list[i + 1]);
            jsonMap.put("index", temp_list[i + 2]);
            jsonMap.put("uuid", temp_list[i + 3]);
            jsonMap.put("pri", temp_list[i + 4]);
            jsonMap.put("rep", temp_list[i + 5]);
            jsonMap.put("docsCount", temp_list[i + 6]);
            jsonMap.put("docsDeleted", temp_list[i + 7]);
            jsonMap.put("storeSize", temp_list[i + 8]);
            jsonMap.put("priStoreSize", temp_list[i + 9]);
            indicesMap.add(jsonMap);
        }

        log.info("indicesList : {}", indicesMap);
        return indicesMap;
    }

    /**
     * index 삭제하기
     *
     * @param serviceId
     * @return 삭제됐는지 True or False
     */
    public boolean deleteIndex(String serviceId) {
        // 1. serviceId와 일치하는 service BulkQuery 가져오기

        boolean isIndexDelete = false;

        // Index 존재하는지 검사
        boolean result = elasticsearchClient.isIndexExist(serviceId);
        if (result) {
            isIndexDelete = elasticsearchClient.IndexDelete(serviceId);
            log.info(">> Success Index Delete : {}", isIndexDelete);
        }
        return isIndexDelete;
    }

    /**
     * 인덱스가 존재하는지 검사
     *
     * @param serviceId
     * @return 존재 여부 True False
     */
    public boolean myIndexExist(String serviceId) {

        return elasticsearchClient.isIndexExist(serviceId);
    }

    /**
     * 색인 건수가 몇개 인지
     *
     * @param serviceId
     * @return 색인 건수 몇개인지
     */
    public long IndexCount(String serviceId) {
        // 1. serviceId와 일치하는 service BulkQuery 가져오기
        com.searchengine.yjpark.domain.Service serviceInfo = serviceService.findServiceByID(serviceId);
        Long dbIdx = serviceInfo.getDbIdx(); // db 정보 가져오기 위해
        String docColumnId = serviceInfo.getIdColumn();
        List<DataBaseInfo> dbInfo = serviceService.findDbByIdx(dbIdx);
        // 2. DB 정보로 동적 연결 - bulk Model 호출
        long countRow = (long)bulkRepository.countCuration(dbInfo, serviceInfo);

        return countRow;
    }
}
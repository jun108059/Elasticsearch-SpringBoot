package com.searchengine.yjpark.api.service;

import com.searchengine.yjpark.admin.service.ServiceService;
import com.searchengine.yjpark.domain.DataBaseInfo;
import com.searchengine.yjpark.domain.Indexing;
import com.searchengine.yjpark.domain.Search;
import com.searchengine.yjpark.es.client.ElasticsearchClient;
import com.searchengine.yjpark.repository.BulkRepository;
import com.searchengine.yjpark.repository.ServiceRepository;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


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

    // 전체 색인(Bulk) 서비스
    // Todo 리턴 타입 꼭 줘야하는지 = Response가 N/A인데 필요한지
    public void bulkIndex(String serviceId) {
        // 1. serviceId와 일치하는 service BulkQuery 가져오기
        com.searchengine.yjpark.domain.Service serviceInfo = serviceService.findServiceByID(serviceId);
        // @Test 코드 Todo 삭제하기 - Model로 옮김
        String bulkQuery = serviceInfo.getBulkQuery();
        Long dbIdx = serviceInfo.getDbIdx(); // db 정보 가져오기 위해
        String docColumnId = serviceInfo.getIdColumn(); //

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
        // (4-1). 만약 있다면 Delete 함수 호출(삭제)
        if(result) {
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
        elasticsearchClient.bulkInsert(bulkRequest);
    }

    /**
     * 검색 서비스 로직
     * @param search
     * @return
     */
    public void searchKeyword(Search search){
        // POST 로 받은 {서비스 ID}, {검색 단어}, {하이라이팅 필드 지정}
        String serviceId = search.getServiceId(); // = Todo indexName (preFix 붙이기)
        String searchText = search.getSearchText();
        // Todo highlight Map에서 column까지 어떻게 접근할 수 있는지(getter?)
//        List<String> fieldList = new
        // 결과필드지정 query 만들어주는 Builder
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 하이라이트 query 만들어주는 Builder
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        // 하이라이트할 field 지정하기
//        for(String field : fieldList){
//            // Todo Test 돌려서 highlight 여러 개 되는지 확인하기
//            searchSourceBuilder.query(QueryBuilders.matchQuery(field, searchText));
//            HighlightBuilder.Field highlightText = new HighlightBuilder.Field(field);
//            highlightText.highlighterType("unified");
//            highlightBuilder.field(highlightText);
//        }
        // 하이라이트 생성
        searchSourceBuilder.highlighter(highlightBuilder);

        // Elastic Client 생성

        // Source field(결과필드), 하이라이팅 꺼내오기
        // 1. 클라이언트가 {서비스ID} + {검색단어} + {...} 선택 -> 검색 or JSON POST
        // 2. JSON Type RequestBody -> Domain 과 Post 매핑 (getter 접근 가능)
        /*****서비스 기능*****/
        // 3. SourceBuilder (Search Query 만들기, 결과 source filter, 하이라이팅 빌더)
        // 4. 서비스ID(IndexName)로 ES Client search 요청
        // 5. return 받은 Response API 정의대로 변환
        // 6. 컨트롤러에 전달

    }

    // 부분 색인 생성 - Create
    public void createDocument(Indexing indexing) {
        // 1. 서비스 ID로 ES 인덱스 접근
        // 2. operation 에 따른 생성/업데이트/삭제 분기
        // 3. 색인 생성(도큐먼트 한개)
        // 3-1. 이미 해당 번호 도큐먼트 존재하는지 여부 확인
        // 3-2. 생성하는 ES 클라이언트 호출
        // 4. 색인 업데이트(도큐먼트 한개 업데이트)
        // 4.1 해당 번호 도큐먼트가 있는지(없으면 튕기기 or 새로 생성)
        // 4.2 업데이트하는 ES 클라이언트 호출
        // 5. 색인 삭제(도큐먼트 한개 삭제)
        // 5-1. 삭제할 도큐먼트가 있는지 (4-1 같은 맥락)
        // 5-2. 삭제하는 ES 클라이언트 호출
        // 색인 완료되면 @return true

        indexing.getServiceId();

    }

/*    // 부분 색인 업데이트 - Update
    public void updateDocument() {
        // Todo Flow 작성해오기

    }

    // 부분 색인 삭제 - Delete
    public void deleteDocument() {
        // Todo Flow 작성해오기

    }*/

}
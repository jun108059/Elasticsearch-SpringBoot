package com.searchengine.yjpark.api.service;

import com.searchengine.yjpark.domain.Search;
import com.searchengine.yjpark.es.client.ElasticsearchClient;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndexService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    // 색인 기능 구현
    // 검색 기능 구현

    @Autowired
    ElasticsearchClient elasticsearchClient;
    // Todo Test 끝난뒤에는 junit 으로 옮기고 지우기
    // index 존재하는지 확인하는 Test
    // 클라이언트 연동되는지!

    public void testIsIndexExist(){
        String indexName = "book";
        boolean result = elasticsearchClient.isIndexExist(indexName);
        log.info("client test : {}", result);

    }

    // 전체 색인 서비스
    public void bulkIndex(String serviceId){
        // Index Request
        String indexId = "my_" + serviceId; // prefix 설정
        IndexRequest request = new IndexRequest(indexId);
        request.id(indexId); // index id 할당
        // 서비스 Id, RDB data -> JSON

        // DataBase 정보는 Form 으로 선택했었음
        // Postman Get 방식으로 테스트하려면 추가로 입력해야할 듯
        // 1. 클라이언트가 Database(Table) 선택 + 서비스ID 입력
        // 서비스 테이블 DB Idx 정보 가져오기
        // 서비스 테이블 - query 로 가져올 Table 확인
        /********* 서비스 로직 시작 ***********/
        // 1. 선택된 정보의 RDB 데이터 가져오기 (스키마 : Table)
        // 2. RDB data를 JSON 형식으로 변환 (MySQL to JSON)
        // 3. prefix + serviceID = indexName 선언
        // 4. JSON 데이터를 ES Client Bulk 함수 호출
            // 4.1 index 있는지 확인
            // 있으면 삭제 (ES 클라이언트)
            // 4.2
        // 5. 컨틀올러에 결과 @return

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
        /*****서비스 기능***/
        // 3. SourceBuilder (Search Query 만들기, 결과 source filter, 하이라이팅 빌더)
        // 4. 서비스ID(IndexName)로 ES Client search 요청
        // 5. return 받은 Response API 정의대로 변환
        // 6. 컨트롤러에 전달

    }

    // 부분 색인 생성 - Create
    public void createDocument() {
        // Todo Flow 작성해오기

    }

    // 부분 색인 업데이트 - Update
    public void updateDocument() {
        // Todo Flow 작성해오기

    }

    // 부분 색인 삭제 - Delete
    public void deleteDocument() {
        // Todo Flow 작성해오기

    }

}
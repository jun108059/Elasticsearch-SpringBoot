package com.searchengine.yjpark.es.client;

import org.apache.http.HttpHost;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

public class ElasticsearchClient {
    // Todo 인덱스 delete 만들어오기!
    // Todo MVC 참고자료 보기
    // Todo Spring Bean 관리 찾아보기

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    // 클라이언트 기능 분리

    // 생성자
    private final RestHighLevelClient elasticsearchClient;


    public ElasticsearchClient(String elasticHost, int elasticPort) {
        elasticsearchClient = new RestHighLevelClient(RestClient.builder(
                new HttpHost(elasticHost, elasticPort, "http")));
    }

    // 클라이언트 부를 getter
    public RestHighLevelClient getElasticsearchClient(){
        return elasticsearchClient;
    }

    // index 존재 여부 검사 함수
    public boolean isIndexExist(String indexName) {

        GetIndexRequest request = new GetIndexRequest(indexName);

        boolean exists = false;
        try {
            exists = elasticsearchClient.indices().exists(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("Request fail : {}", e.getMessage());
            //Todo Error 핸들링
        }

        return exists;
    }
    
    // 데이터 Bulk 삽입
    public void bulkInsert(BulkRequest request) {

        // Index 생성(서비스에서 생성 됨)

        elasticsearchClient.bulkAsync(request, RequestOptions.DEFAULT, new ActionListener<BulkResponse>() {
            // 비동기 처리이기 나중에 응답 받을 Listener
            @Override
            public void onResponse(BulkResponse bulkItemResponses) {
                log.info("Bulk Success");
            }

            @Override
            public void onFailure(Exception e) {
                // 로그 남길 수 있고 핸들링 할 수 있음
                log.error("Bulk Fail");
            }
        });
    }

    // document 생성
    public boolean documentCreate(String indexName, String documentId, Map<String, Object> jsonMap){
        // 어떤 인덱스에 넣을지 = indexName
        // 어떤 데이터를 넣을지 = jsonMap
        boolean result = false;
        IndexRequest request = new IndexRequest(indexName);
        request.id(documentId).source(jsonMap);

        try {
            IndexResponse indexResponse = elasticsearchClient.index(request, RequestOptions.DEFAULT);
            // 성공 여부 : true or false 반환
            result = indexResponse.getResult() == DocWriteResponse.Result.CREATED;
        } catch (Exception e) {
            log.error("Save Failed : {}", e.getMessage());
        }
        return result;
    }

    // document 업데이트
    public boolean documentUpdate(String indexName, String documentId, Map<String, Object> jsonMap) {

        boolean result = false;

        UpdateRequest request = new UpdateRequest("indexName", "documentId");
        request.doc(jsonMap);

        try {
            UpdateResponse updateResponse = elasticsearchClient.update(request, RequestOptions.DEFAULT);
            result = updateResponse.getResult() == DocWriteResponse.Result.UPDATED;
        } catch (IOException e) {
            log.error("Update Failed : {}", e.getMessage());
        }
        return result;
    }

    // document 삭제
    public boolean documentDelete(String indexName, String documentId) {
        DeleteRequest request = new DeleteRequest(indexName, documentId);

        boolean result = false;
        try {
            DeleteResponse deleteResponse = elasticsearchClient.delete(request, RequestOptions.DEFAULT);
            result = deleteResponse.getResult() == DocWriteResponse.Result.DELETED;
        } catch (IOException e) {
            log.error("Delete Failed : {}", e.getMessage());
        }
        return result;
    }

    // 검색
    public SearchResponse search(String indexName, SearchSourceBuilder searchSourceBuilder) {
        // Request 요청 만들기
        SearchRequest searchRequest = new SearchRequest(indexName);
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = null;

        try {
            // 동기 Execution
            searchResponse = elasticsearchClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("Search Response Fail : {}", e.getMessage());
        }

        return searchResponse;
        // 서비스 로직에서 Map으로 Source, highlighting 꺼내오기
    }

    // 인덱스 리스트 가져오기
    public boolean getIndexList(String prefixIndexName) {
        // Todo Low Level로 리스트 가져오는거 만들어 오기
        // High Level에 없음

        //elasticsearchClient.getLowLevelClient().performRequest();
        // 검색 엔진에 색인된 모든 index List 가져오기
        GetIndexRequest request = new GetIndexRequest(prefixIndexName);
        GetIndexResponse getIndexResponse = null;
        try {
            getIndexResponse = elasticsearchClient.indices().get(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        getIndexResponse.getIndices();
        return true;
    }

}

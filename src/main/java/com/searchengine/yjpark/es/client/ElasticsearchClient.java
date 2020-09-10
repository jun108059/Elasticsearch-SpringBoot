package com.searchengine.yjpark.es.client;

import org.apache.http.HttpHost;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.Index;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
public class ElasticsearchClient {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    // 클라이언트 기능 분리
    @Value("${elasticsearch.host}")
    private String elasticHost;

    @Value("${elasticsearch.port}")
    private int elasticPort;

    // 생성자
    private final RestHighLevelClient elasticsearchClient;

    public ElasticsearchClient(){
        elasticsearchClient = new RestHighLevelClient(RestClient.builder(
                new HttpHost(elasticHost, elasticPort, "http")));
    }

    // 클라이언트 부를 getter
    public RestHighLevelClient getElasticsearchClient(){
        return elasticsearchClient;
    }

    // index 존재 여부 검사 함수
    public boolean isIndexExist(){

        return true;
    }
    
    // 데이터 Bulk 삽입
    public void bulkInsert(BulkRequest request) {

        // Index 생성(서비스에서 생성 됨)

        elasticsearchClient.bulkAsync(request, RequestOptions.DEFAULT, new ActionListener<BulkResponse>() {
            // 비동기 처리 이기 나중에 응답 받을 Listener
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
    public boolean search(String indexName, QueryBuilders queryBuilders, String keyWord, List<String> stringList) {
        // QueryBuilders를 만들어서 받아오면 됨!

        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        // Todo Test 돌려서 highlight 여러 개 되는지
        for(String field : stringList){
            searchSourceBuilder.query(QueryBuilders.matchQuery(field, keyWord));
            HighlightBuilder.Field highlightText = new HighlightBuilder.Field(field);
            highlightText.highlighterType("unified");
            highlightBuilder.field(highlightText);
        }
        searchRequest.source(searchSourceBuilder);
        searchSourceBuilder.highlighter(highlightBuilder);

        return true;
    }

    // 인덱스 리스트 가져오기
    public boolean getIndexList() {
        // 검색 엔진에 색인된 모든 index List 가져오기
        // return
        return true;
    }

}

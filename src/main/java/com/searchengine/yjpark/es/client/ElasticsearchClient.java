package com.searchengine.yjpark.es.client;

import org.apache.http.HttpHost;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.*;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

public class ElasticsearchClient {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    // 생성자
    private final RestHighLevelClient restHighLevelClient;

    public ElasticsearchClient(String elasticHost, int elasticPort) {
        restHighLevelClient = new RestHighLevelClient(RestClient.builder(
                new HttpHost(elasticHost, elasticPort, "http")));
    }

    // 클라이언트 부를 getter
    public RestHighLevelClient getRestHighLevelClient(){
        return restHighLevelClient;
    }

    /**
     * index Response 실행
     * @param indexRequest
     */
    public void indexCreate(IndexRequest indexRequest){
        try {
            IndexResponse indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("Index Create Response fail : {}", e.getMessage());
        }
    }

    /**
     * index 존재 여부 검사 함수
     * @param indexName
     * @return boolean
     */
    public boolean isIndexExist(String indexName) {

        GetIndexRequest request = new GetIndexRequest(indexName);

        boolean exists = false;
        try {
            exists = restHighLevelClient.indices().exists(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("Request fail : {}", e.getMessage());
            //Todo Error 핸들링
        }

        return exists;
    }

    /**
     * index 삭제 함수
     * @param indexName
     * @return boolean
     */
    public boolean IndexDelete(String indexName) {

        DeleteIndexRequest request = new DeleteIndexRequest(indexName);

        boolean acknowledged = false;
        try {
            AcknowledgedResponse deleteIndexResponse = restHighLevelClient.indices().delete(request, RequestOptions.DEFAULT);
            acknowledged = deleteIndexResponse.isAcknowledged();
        } catch (IOException e) {
            log.error("Index Delete Request fail : {}", e.getMessage());
        }

        return acknowledged;
    }

    /**
     * 전체 데이터 Bulk(색인)
     * @param request
     */
    public boolean bulkInsert(BulkRequest request) {
        // Todo Sync로 바꾸고 return 성공 실패 받아서 반환
        // @param 서비스 로직에서 생성된 Request

        boolean bulkResult = false;
        try {
            BulkResponse bulkResponse = restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
            bulkResult = bulkResponse.hasFailures();
        } catch (IOException e) {
            log.error("Bulk Fail : {}", e.getMessage());
        }
        return bulkResult;
    }

    /**
     * ES document 생성
     * @param indexName
     * @param documentId
     * @param jsonMap
     * @return boolean
     */
    public boolean documentCreate(String indexName, String documentId, Map<String, Object> jsonMap){
        // 어떤 인덱스에 넣을지 = indexName
        // 어떤 데이터를 넣을지 = jsonMap
        boolean result = false;
        IndexRequest request = new IndexRequest(indexName);
        request.id(documentId).source(jsonMap);

        try {
            IndexResponse indexResponse = restHighLevelClient.index(request, RequestOptions.DEFAULT);
            // 성공 여부 : true or false 반환
            result = indexResponse.getResult() == DocWriteResponse.Result.CREATED;
        } catch (Exception e) {
            log.error("Save Failed : {}", e.getMessage());
        }
        return result;
    }

    /**
     * ES document 업데이트
     * @param indexName
     * @param documentId
     * @param jsonMap
     * @return boolean
     */
    public boolean documentUpdate(String indexName, String documentId, Map<String, Object> jsonMap) {

        boolean result = false;

        UpdateRequest request = new UpdateRequest("indexName", "documentId");
        request.doc(jsonMap);

        try {
            UpdateResponse updateResponse = restHighLevelClient.update(request, RequestOptions.DEFAULT);
            result = updateResponse.getResult() == DocWriteResponse.Result.UPDATED;
        } catch (IOException e) {
            log.error("Update Failed : {}", e.getMessage());
        }
        return result;
    }

    /**
     * ES document 삭제
     * @param indexName
     * @param documentId
     * @return boolean
     */
    public boolean documentDelete(String indexName, String documentId) {
        DeleteRequest request = new DeleteRequest(indexName, documentId);

        boolean result = false;
        try {
            DeleteResponse deleteResponse = restHighLevelClient.delete(request, RequestOptions.DEFAULT);
            result = deleteResponse.getResult() == DocWriteResponse.Result.DELETED;
        } catch (IOException e) {
            log.error("Delete Failed : {}", e.getMessage());
        }
        return result;
    }

    /**
     * 검색
     * @param indexName
     * @param searchSourceBuilder
     * @return SearchResponse
     */
    public SearchResponse search(String indexName, SearchSourceBuilder searchSourceBuilder) {
        // Request 요청 만들기
        SearchRequest searchRequest = new SearchRequest(indexName);
        searchRequest.source(searchSourceBuilder);
        SearchResponse Response = null;

        log.info("searchRequest: {}",searchRequest);

        try {
            // 동기 Execution
            Response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("Search Response Fail : {}", e.getMessage());
        }

        // Todo 빈 리스트 응답으로 줘도 됨
        return Response;
        // 서비스 로직에서 Map으로 Source, highlighting 꺼내오기
    }

    /**
     * 인덱스 리스트 가져오기
     * @return boolean
     */
    public String getIndexList() {
        // 검색 엔진에 색인된 모든 index List 가져오기

        // Todo Low Level로 리스트 가져오는거 만들어 오기

        String responseBody = "";
        // High Level에 없음
        RestClient lowLevelClient = restHighLevelClient.getLowLevelClient();

        // https://www.elastic.co/guide/en/elasticsearch/reference/current/cat-indices.html
        // https://stackoverflow.com/questions/54181844/how-to-get-the-total-count-of-documnets-present-in-an-index-using-java-high-leve/54185106#54185106
        Request request = new Request("GET", "/_cat/indices");
        try {
            Response response = lowLevelClient.performRequest(request);
            responseBody = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            log.error("cat indices Fail : {},", e.getMessage());
        }

        return responseBody;
    }

    /**
     * Document 존재 여부 검사 함수
     * @param indexName
     * @return boolean
     */
    public boolean isDocumentExist(String indexName, String documentId) {

        GetRequest getRequest = new GetRequest(indexName, documentId);
        // 공식문서에서 단순히 Exist 확인만 하기 때문에 source 패치와 fields 저장을 비활성화 추천
        getRequest.fetchSourceContext(new FetchSourceContext(false));
        getRequest.storedFields("_none_");

        boolean exists = false;
        try {
            exists = restHighLevelClient.exists(getRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("Document Exist check fail : {}", e.getMessage());
        }

        return exists;
    }

}

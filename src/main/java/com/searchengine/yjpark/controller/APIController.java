package com.searchengine.yjpark.controller;

import com.searchengine.yjpark.domain.DataBaseInfo;
import com.searchengine.yjpark.domain.Service;
import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.alias.Alias;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/simple/search")
public class APIController {


    @Autowired
    RestHighLevelClient restHighLevelClient;

    @RequestMapping("/bulk")
    public void bulkIndex() {
        try {
            SearchRequest searchRequest = new SearchRequest();
            searchRequest.indices("");
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(QueryBuilders.matchAllQuery());
            searchRequest.source(searchSourceBuilder);

            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            System.out.println(searchResponse);

            //index name
            String indexName = "search_engine";
            //type name
            String typeName = "service";

            //settings
            XContentBuilder settingsBuilder = XContentFactory.jsonBuilder();
            settingsBuilder.startObject();
            {
                settingsBuilder.field("number_of_shards",5);
                settingsBuilder.field("number_of_replicas",1);
            }
            settingsBuilder.endObject();

            //mapping info
            XContentBuilder indexBuilder = XContentFactory.jsonBuilder();
            indexBuilder.startObject();
            {
                indexBuilder.startObject(typeName)
                        .startObject("properties")
                        .startObject("idx")
                        .field("type", "integer")
                        .endObject()
                        .startObject("service_id")
                        .field("type","text")
                        .endObject()
                        .startObject("service_detail")
                        .field("type","text")
                        .endObject()
                        .startObject("bulk_query")
                        .field("type","text")
                        .endObject()
                        .startObject("db_conn_ip")
                        .field("type","text")
                        .endObject()
                        .endObject()
                        .endObject();
            }
            indexBuilder.endObject();

            //인덱스생성 요청 객체
            CreateIndexRequest request = new CreateIndexRequest(indexName);
            //세팅 정보
            request.settings(settingsBuilder);
            //매핑 정보
            request.mapping(indexBuilder); //어떤 type인지 명시를 안했는데...

            //인덱스생성
            CreateIndexResponse response = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);

            XContentBuilder builder = XContentFactory.jsonBuilder();
            builder.startObject();
            {
                builder.field("user", "youngjun");
                builder.timeField("postDate", new Date());
                builder.field("message", "trying out Elasticsearch");
            }
            builder.endObject();
            IndexRequest indexRequest = new IndexRequest("posts")
                    .id("1").source(builder);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/delete")
    public Object delete() {

        boolean acknowledged = false;

        try(
                RestHighLevelClient client = createConnection()
        ){

            //index name
            String indexName = "saramin";

            //인덱스 삭제 요청 객체
            DeleteIndexRequest request = new DeleteIndexRequest(indexName);
            AcknowledgedResponse deleteIndexResponse = client.indices().delete(request, RequestOptions.DEFAULT);
            acknowledged = deleteIndexResponse.isAcknowledged();
        }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return "인덱스 삭제에 실패하였습니다. - catch";
        }

        return acknowledged == true ? "인덱스 삭제가 완료되었습니다.":"인덱스 삭제에 실패하였습니다.";
    }

    // Bulk API
    @GetMapping("/simple/search/bulk")
    public String bulkAPI(Model model) {
        // 저장된 DB 정보 가져오기
        List<DataBaseInfo> dbInfo= serviceService.findAllDBInfo();
        model.addAttribute("dbInfo", dbInfo);
        return "simple/serviceInfo";
    }


    // 색인 조회 page
    @GetMapping("/simple/search/contents")
    public String contentsPage(Model model) {
/*        int page = 0;
        int size = 10;

        if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
            page = Integer.parseInt(request.getParameter("page")) - 1;
        }

        if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
            size = Integer.parseInt(request.getParameter("size"));
        }*/

        // 저장된 Service 정보 가져오기
        List<Service> services = serviceService.findAllService();
        model.addAttribute("services", services);
        return "simple/search/contents";
    }
}
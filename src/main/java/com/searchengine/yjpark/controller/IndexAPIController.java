package com.searchengine.yjpark.controller;

import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.alias.Alias;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/es/index")
public class IndexAPIController {

    /*
     * connection create method
     */
    public RestHighLevelClient createConnection() {
        return new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("127.0.0.1",9200,"http")
                )
        );
    }

    @RequestMapping("/create")
    public Object createIndex() {

        boolean acknowledged = false;

        try(
                RestHighLevelClient client = createConnection()
        ){
            //index name
            String indexName = "saramin";
            //type name
            String typeName = "curation";

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
                        .startObject("curation_seq")
                            .field("type", "integer")
                        .endObject()
                        .startObject("curation_title")
                            .field("type","text")
                        .endObject()
                        .startObject("template")
                           .field("type","text")
                        .endObject()
                        .startObject("template_img")
                            .field("type","text")
                        .endObject()
                        .startObject("ending_txt")
                            .field("type","text")
                        .endObject()
                        .startObject("tag")
                            .field("type","text")
                        .endObject()
                        .startObject("pc_hits")
                            .field("type","integer")
                        .endObject()
                        .startObject("m_hits")
                            .field("type","integer")
                        .endObject()
                        .startObject("reservation_dt")
                            .field("type","date")
                        .endObject()
                        .startObject("reg_dt")
                            .field("type","date")
                        .endObject()
                        .startObject("up_dt")
                            .field("type","date")
                        .endObject()
                        .startObject("status")
                            .field("type","text")
                        .endObject()
                        .startObject("open_fl")
                            .field("type","text")
                        .endObject()
                        .startObject("main_fl")
                            .field("type","text")
                        .endObject()
                        .startObject("writer")
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


            //별칭설정
            String aliasName = "curationType";
            request.alias(new Alias(aliasName));

            //인덱스생성
            CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);

            acknowledged = response.isAcknowledged();



        }catch (Exception e) {
            e.printStackTrace();
            return "인덱스 생성에 실패하였습니다. - catch";
        }

        return acknowledged == true ? "인덱스가 생성되었습니다.":"인덱스생성에 실패하였습니다.";
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
}
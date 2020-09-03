package com.searchengine.yjpark.controller;

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
            String indexName = "sample-korean-index";
            //type name
            String typeName = "_doc";

            //settings
            XContentBuilder settingsBuilder = XContentFactory.jsonBuilder()
                    .startObject()
                    .field("number_of_shards",5)
                    .field("number_of_replicas",1)

                    .startObject("analysis")
                    .startObject("tokenizer")
                    .startObject("sample-nori-tokenizer")
                    .field("type","nori_tokenizer")
                    .field("decompound_mode","mixed")
                    .field("user_dictionary","user_dictionary.txt")
                    .endObject()
                    .endObject()

                    .startObject("analyzer")
                    .startObject("sample-nori-analyzer")
                    .field("type","custom")
                    .field("tokenizer","sample-nori-tokenizer")
                    .array("filter",new String[]{
                                    "sample-nori-posfilter",
                                    "nori_readingform",
                                    "sample-synonym-filter",
                                    "sample-stop-filter"
                            }
                    )
                    .endObject()
                    .endObject()

                    .startObject("filter")
                    .startObject("sample-nori-posfilter")
                    .field("type","nori_part_of_speech")
                    .array("stoptaags",new String[] {
                                    "E","IC","J","MAG","MM","NA","NR","SC",
                                    "SE","SF","SH","SL","SN","SP","SSC","SSO",
                                    "SY","UNA","UNKNOWN","VA","VCN","VCP","VSV",
                                    "VV","VX","XPN","XR","XSA","XSN","XSV"
                            }
                    )
                    .endObject()

                    .startObject("sample-synonym-filter")
                    .field("type","synonym")
                    .field("synonyms_path","synonymsFilter.txt")
                    .endObject()

                    .startObject("sample-stop-filter")
                    .field("type","stop")
                    .field("stopwords_path","stopFilter.txt")
                    .endObject()
                    .endObject()
                    .endObject()
                    .endObject();

            //mapping info
            XContentBuilder indexBuilder = XContentFactory.jsonBuilder()
                    .startObject()
                    .startObject(typeName)
                    .startObject("properties")
                    .startObject("question")
                    .field("type","text")
                    .field("analyzer","sample-nori-analyzer")
                    .endObject()
                    .startObject("answer")
                    .field("type","keyword")
                    .endObject()
                    .endObject()
                    .endObject()
                    .endObject();

            //인덱스생성 요청 객체
            CreateIndexRequest request = new CreateIndexRequest(indexName);
            //세팅 정보
            request.settings(settingsBuilder);
            //매핑 정보
            request.mapping(typeName, indexBuilder);

            //별칭설정
            String aliasName = "chatbotInstance";
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
            String indexName = "sample-korean-index";

            //인덱스 삭제 요청 객체
            DeleteIndexRequest request = new DeleteIndexRequest(indexName);

            DeleteIndexResponse response = client.indices().delete(request, RequestOptions.DEFAULT);

            acknowledged = response.isAcknowledged();
        }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return "인덱스 삭제에 실패하였습니다. - catch";
        }

        return acknowledged == true ? "인덱스 삭제가 완료되었습니다.":"인덱스 삭제에 실패하였습니다.";
    }

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
}
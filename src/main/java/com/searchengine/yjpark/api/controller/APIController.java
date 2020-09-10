
package com.searchengine.yjpark.api.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/simple/search")
public class APIController {
    // 크게 검색 & 색인
    @Autowired
    RestHighLevelClient restHighLevelClient;

    @RequestMapping("/bulk")
    @GetMapping("args")
    public void bulkIndex(@RequestParam(value = "id")String id) {
        // Index Request
        String indexId = "myIndex_" + id; // prefix 설정
        IndexRequest request = new IndexRequest(indexId);
        request.id(indexId); // index id 할당



    }

}


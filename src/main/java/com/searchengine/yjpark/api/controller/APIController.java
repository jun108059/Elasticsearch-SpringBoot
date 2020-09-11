
package com.searchengine.yjpark.api.controller;

import com.searchengine.yjpark.api.response.SearchResponse;
import com.searchengine.yjpark.api.service.IndexService;
import com.searchengine.yjpark.domain.Indexing;
import com.searchengine.yjpark.domain.Search;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/simple/search")
public class APIController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    // 크게 검색 & 색인
    @Autowired
    RestHighLevelClient restHighLevelClient;

    @Autowired
    IndexService indexService;

    @GetMapping("/bulk")
    public void bulkIndex(@RequestParam(value = "service_id")String serviceId) {

        log.info("Bulk Get : {}", serviceId);

        IndexService indexService;

    }

    // simple/search/contents/{serviceId}/{keyWord}
    @PostMapping("/contents")
    public void searchKeyword(@RequestBody Search search) {

        log.info("Search POST Test : {}", search.toString());
    }

    @PostMapping("/indexing/id")
    public void indexingDocument(@RequestBody Indexing indexing) {

        log.info("Search POST Test : {}", indexing.toString());
    }

    @GetMapping("searchResponse")
    public SearchResponse getSearchResponse() {
        SearchResponse searchResponse = new SearchResponse();
        searchResponse.setTotal(1233);

        indexService.testIsIndexExist();

        return searchResponse;
    }



}


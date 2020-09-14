
package com.searchengine.yjpark.api.controller;

import com.searchengine.yjpark.api.response.SearchResponse;
import com.searchengine.yjpark.api.service.IndexService;
import com.searchengine.yjpark.domain.Indexing;
import com.searchengine.yjpark.domain.Search;
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
        // 서비스 로직 호출
        indexService.bulkIndex(serviceId);
        // Todo 컨트롤러에서 Response 반환할 필요가 있는지
    }

    // simple/search/contents/{serviceId}/{keyWord}
    @PostMapping("/contents")
    public void searchKeyword(@RequestBody Search search) {
        // 클라이언트가 {서비스ID} + {검색단어} + {...} 선택 -> 검색 or JSON POST
        log.info("Search POST Test : {}", search.toString());
        // 서비스 로직 호출
        indexService.searchKeyword(search);
    }

    @GetMapping("searchResponse")
    public SearchResponse getSearchResponse() {
        SearchResponse searchResponse = new SearchResponse();
        searchResponse.setTotal(1233);

        indexService.testIsIndexExist();

        return searchResponse;
    }

    @PostMapping("/indexing/id")
    public void indexingDocument(@RequestBody Indexing indexing) {
        // 컨텐츠 부분 색인
        // indexing > 서비스ID, 연산 타입, 컨텐츠 ID
        // Todo 컨텐츠 id == 도큐먼트 ID?
//        indexService
        log.info("Search POST Test : {}", indexing.toString());
    }
}


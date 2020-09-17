
package com.searchengine.yjpark.api.controller;

import com.searchengine.yjpark.api.response.SearchResultResponse;
import com.searchengine.yjpark.api.service.IndexService;
import com.searchengine.yjpark.domain.AjaxResponseBody;
import com.searchengine.yjpark.domain.Indexing;
import com.searchengine.yjpark.domain.Search;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;


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
    public RedirectView bulkIndex(@RequestParam(value = "service_id") String serviceId) {
        log.info("Bulk Get : {}", serviceId);
        // 서비스 로직 호출

        boolean bulkSuccess = indexService.bulkIndex(serviceId);
        log.info("bulkSuccess : {}", bulkSuccess);
        if (bulkSuccess) {
            return new RedirectView("/simple/viewBulkIndex");
        } else {
            return new RedirectView("redirect:/simple/error");
        }
    }

    // simple/search/contents/{serviceId}/{keyWord}
    @PostMapping("/contents")
    public SearchResultResponse searchKeyword(@RequestBody Search search) {
        // 클라이언트가 {서비스ID} + {검색단어} + {...} 선택 -> 검색 or JSON POST
        log.info("> Search POST Test : {}", search.toString());
        // 서비스 로직 호출
        SearchResultResponse searchResultResponse = new SearchResultResponse();
        return indexService.searchKeyword(search);
    }

    @PostMapping("/indexing/id")
    public void indexingCreateDocument(@RequestBody Indexing indexing) {
        // 컨텐츠 부분 색인 (생성)
        // indexing > 서비스ID, 연산 타입, 컨텐츠 ID
        log.info("Search POST Test : {}", indexing.toString());
        indexService.createDocument(indexing);
    }

    @PutMapping("/indexing/id")
    public void indexingUpdateDocument(@RequestBody Indexing indexing) {
        // 컨텐츠 부분 색인 (수정)
        // indexing > 서비스ID, 연산 타입, 컨텐츠 ID
        log.info("Search PUT Test : {}", indexing.toString());
        indexService.updateDocument(indexing);
    }

    @DeleteMapping("/indexing/id")
    public void indexingDeleteDocument(@RequestBody Indexing indexing) {
        // 컨텐츠 부분 색인 (삭제)
        // indexing > 서비스ID, 연산 타입, 컨텐츠 ID
        log.info("Search DELETE Test : {}", indexing.toString());
        indexService.deleteDocument(indexing);
    }

    @GetMapping("/delete")
    public RedirectView deleteIndex(@RequestParam(value = "service_id") String serviceId) {
        log.info("Delete Get : {}", serviceId);
        // 서비스 로직 호출
        boolean deleteSuccess = indexService.deleteIndex(serviceId);
        if (deleteSuccess) {
            return new RedirectView("/simple/viewBulkIndex");
        } else {
            return new RedirectView("redirect:/simple/error");
        }
    }

    @PostMapping("/checkIndexExist")
    public ResponseEntity<?> indexCheck(@RequestBody String serviceId) {
        AjaxResponseBody result = new AjaxResponseBody();

        if (indexService.myIndexExist(serviceId)) {
            result.setMsg("Exist");
            result.setCount(indexService.IndexCount(serviceId));
        }else {
            result.setMsg("NotExist");
        }
        return ResponseEntity.ok(result);
    }

}


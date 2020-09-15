package com.searchengine.yjpark.api.response;

import java.util.List;
import java.util.Map;

public class SearchResultResponse {

    private int total;
    private int page;
    private int pageSize;
    private List<Map<String, Object>> result;
    // Request 전문 검색 조회 결과 컬럼 받기

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int page_size) {
        this.pageSize = page_size;
    }

    public List<Map<String, Object>> getResult() { return result; }

    public void setResult(List<Map<String, Object>> result) {
        this.result = result;
    }

}

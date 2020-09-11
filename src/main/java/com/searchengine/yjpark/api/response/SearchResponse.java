package com.searchengine.yjpark.api.response;

import java.util.List;

public class SearchResponse {

    private int total;
    private int page;
    private int page_size;
    private List<String> result;
    // Request 전문 검색 조회 결과 컬럼 받기
    private List<String> highlight;

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

    public int getPage_size() {
        return page_size;
    }

    public void setPage_size(int page_size) {
        this.page_size = page_size;
    }

    public List<String> getResult() {
        return result;
    }

    public void setResult(List<String> result) {
        this.result = result;
    }

    public List<String> getHighlight() {
        return highlight;
    }

    public void setHighlight(List<String> highlight) {
        this.highlight = highlight;
    }
    // Request 전문 하이라이팅 지정 컬럼 정보

}

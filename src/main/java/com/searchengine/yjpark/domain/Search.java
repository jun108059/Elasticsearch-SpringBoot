package com.searchengine.yjpark.domain;

import java.util.List;
import java.util.Map;

public class Search {

    private String serviceId;
    private String searchText;
    private List<String> resultColumns;
    private Map<String, Object> highlight;
    private int pageSize;
    private int pageNo;


    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public Map<String, Object> getHighlight() {
        return highlight;
    }

    public void setHighlight(Map<String, Object> highlight) {
        this.highlight = highlight;
    }

    public List<String> getResultColumns() {
        return resultColumns;
    }

    public void setResultColumns(List<String> resultColumns) {
        this.resultColumns = resultColumns;
    }

    @Override
    public String toString() {
        return "Search{" +
                "serviceId='" + serviceId + '\'' +
                ", searchText='" + searchText + '\'' +
                ", resultColumns=" + resultColumns +
                ", highlight=" + highlight +
                ", pageSize=" + pageSize +
                ", pageNo=" + pageNo +
                '}';
    }
}

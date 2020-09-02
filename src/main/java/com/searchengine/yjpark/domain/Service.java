package com.searchengine.yjpark.domain;

import java.util.List;

public class Service {

    private Long idx;
    private String serviceId;
    private String serviceDetail;
    private String bulkQuery;
    private String dbInfo;

    public Long getId() {
        return idx;
    }

    public void setId(Long idx) {
        this.idx = idx;
    }


    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceDetail() {
        return serviceDetail;
    }

    public void setServiceDetail(String serviceDetail) {
        this.serviceDetail = serviceDetail;
    }

    public String getBulkQuery() {
        return bulkQuery;
    }

    public void setBulkQuery(String bulkQuery) {
        this.bulkQuery = bulkQuery;
    }


    public String getDbInfo() {
        return dbInfo;
    }

    public void setDbInfo(String dbInfo) {
        this.dbInfo = dbInfo;
    }
}

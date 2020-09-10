package com.searchengine.yjpark.domain;

import java.time.LocalDateTime;
import java.util.List;

public class Service {

    private Long idx;
    private String serviceId;
    private String serviceDetail;
    private String bulkQuery;
    private Long dbIdx;
    private String idColume;
    private LocalDateTime createDt;
    private LocalDateTime updateDt;

    public String getIdColume() {
        return idColume;
    }

    public void setIdColume(String idColume) {
        this.idColume = idColume;
    }


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


    public Long getDbIdx() {
        return dbIdx;
    }

    public void setDbIdx(Long dbIdx) {
        this.dbIdx = dbIdx;
    }
}

package com.searchengine.yjpark.domain;

import java.util.List;

public class Indexing {

    private String serviceId;
    private String operationType;
    private String contentsIdValue;
    private List<String> columns;

    public List<String> getColumns() {
        return columns;
    }

    public void setColumns(List<String> columns) {
        this.columns = columns;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public String getContentsIdValue() {
        return contentsIdValue;
    }

    public void setContentsIdValue(String contentsIdValue) {
        this.contentsIdValue = contentsIdValue;
    }

    @Override
    public String toString() {
        return "Indexing{" +
                "serviceId='" + serviceId + '\'' +
                ", operationType='" + operationType + '\'' +
                ", contentsIdValue='" + contentsIdValue + '\'' +
                '}';
    }
}

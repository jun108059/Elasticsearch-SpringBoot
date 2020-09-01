package com.searchengine.yjpark.domain;

public class DataBaseInfo {

    private Long id;
    private String host;
    private String dbId;
    private String dbPw;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getDbId() {
        return dbId;
    }

    public void setDbId(String dbId) {
        this.dbId = dbId;
    }

    public String getDbPw() {
        return dbPw;
    }

    public void setDbPw(String dbPw) {
        this.dbPw = dbPw;
    }


}

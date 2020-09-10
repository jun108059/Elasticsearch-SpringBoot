package com.searchengine.yjpark.domain;

public class DataBaseInfo {

    private Long idx;
    private String dbConnIp;
    private String dbId;
    private String dbPw;


    public Long getIdx() {
        return idx;
    }

    public void setIdx(Long idx) {
        this.idx = idx;
    }

    public String getDbConnIp() {
        return dbConnIp;
    }

    public void setDbConnIp(String dbConnIp) {
        this.dbConnIp = dbConnIp;
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

package com.searchengine.yjpark.api.service;

import com.searchengine.yjpark.domain.DataBaseInfo;
import com.searchengine.yjpark.repository.BulkRepository;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;

public class IndexService {
    // 색인 기능 구현
    // 1. 전체 색인
    // 2. 부분 색인

    @Autowired
    RestHighLevelClient restHighLevelClient;
    //ESclient

    // 전체 : bulk
    // 부분 : save / update / delete
    public void bulkAPI(){
        // 서비스 Id, RDB data -> JSON
    }



}

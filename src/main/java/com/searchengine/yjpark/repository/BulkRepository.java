package com.searchengine.yjpark.repository;

import com.searchengine.yjpark.domain.DataBaseInfo;

import java.util.List;
import java.util.Map;

public interface BulkRepository {
    int countCuration(List<DataBaseInfo> dbInfo, com.searchengine.yjpark.domain.Service serviceInfo);

    List<Map<String, Object>> dynamicMapping(List<DataBaseInfo> dbInfo, com.searchengine.yjpark.domain.Service serviceInfo);
}
package com.searchengine.yjpark.repository;

import com.searchengine.yjpark.domain.DataBaseInfo;

import java.util.List;

public interface SearchRepository {
    int countDocument(List<DataBaseInfo> dbInfo, com.searchengine.yjpark.domain.Service serviceInfo);
}
package com.searchengine.yjpark.repository;

import com.searchengine.yjpark.domain.DataBaseInfo;
import com.searchengine.yjpark.domain.Service;

import java.util.List;
import java.util.Optional;

public interface BulkRepository {
    void bulk(DataBaseInfo dataBaseInfo, Service service);
}

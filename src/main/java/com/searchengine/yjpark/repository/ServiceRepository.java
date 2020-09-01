package com.searchengine.yjpark.repository;

import com.searchengine.yjpark.domain.DataBaseInfo;

import java.util.List;
import java.util.Optional;

public interface ServiceRepository {
    DataBaseInfo save(DataBaseInfo dataBaseInfo);
    Optional<DataBaseInfo> findById(Long id);
    List<DataBaseInfo> findAll();
}

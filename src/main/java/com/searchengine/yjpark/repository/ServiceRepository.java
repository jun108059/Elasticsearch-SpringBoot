package com.searchengine.yjpark.repository;

import com.searchengine.yjpark.domain.DataBaseInfo;
import com.searchengine.yjpark.domain.Service;

import java.util.List;
import java.util.Optional;

public interface ServiceRepository {
    DataBaseInfo save(DataBaseInfo dataBaseInfo);
    Optional<DataBaseInfo> findByHost(String host);
    List<DataBaseInfo> findAll();

    Service saveService(Service service);
    List<Service> findAllService();
}

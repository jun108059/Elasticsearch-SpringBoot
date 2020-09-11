package com.searchengine.yjpark.admin.service;

import com.searchengine.yjpark.domain.DataBaseInfo;
import com.searchengine.yjpark.domain.Service;
import com.searchengine.yjpark.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class ServiceService {

    private final ServiceRepository serviceRepository;

    @Autowired
    public ServiceService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    /**
     * 데이터베이스 정보 등록
     * @param dataBaseInfo
     *
     */
    public void registrationDB(DataBaseInfo dataBaseInfo) {

        serviceRepository.save(dataBaseInfo);
//        return dataBaseInfo.getDbConnIp();
    }


    /**
     * 전체 데이터베이스 정보 조회
     * @return
     */
    public List<DataBaseInfo> findAllDBInfo() {
        return serviceRepository.findAll();
    }

    /**
     * host 정보로 db Info 불러오기
     * @param host
     * @return
     */
    public Optional<DataBaseInfo> findOne(String host) {
        return serviceRepository.findByHost(host);
    }

    /**
     * 서비스 등록
     * @param
     * @return
     */
    public void registrationService(Service service) {

        serviceRepository.saveService(service);
//        return service.getServiceId();
    }

    /**
     * 전체 서비스 정보 조회
     * @return
     */
    public List<Service> findAllService() {
        return serviceRepository.findAllService();
    }

    /**
     * ID에 맞는 서비스 정보 조회
     * @return
     */
    public List<Service> findServiceByID(String id) {
        return serviceRepository.findServiceById(id);
    }
}

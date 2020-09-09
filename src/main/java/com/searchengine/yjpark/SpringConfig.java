package com.searchengine.yjpark;

import com.searchengine.yjpark.repository.*;
import com.searchengine.yjpark.service.ServiceService;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    private final DataSource dataSource;

    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public ServiceService serviceService() { return new ServiceService(serviceRepository());}

    @Bean
    public ServiceRepository serviceRepository() {
        // 구현체 생성
        return new JdbcTemplateServiceRepository(dataSource);
    }


}

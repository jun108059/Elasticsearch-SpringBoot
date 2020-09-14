package com.searchengine.yjpark;

import com.searchengine.yjpark.repository.*;
import com.searchengine.yjpark.admin.service.ServiceService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    private final DataSource dataSource;

    public SpringConfig(@Qualifier("dataSource") DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public ServiceRepository serviceRepository() {
        // 구현체 생성
        return new JdbcTemplateServiceRepository(dataSource);
    }

    @Bean
    public BulkRepository bulkRepository() {
        return new JdbcTemplateBulkRepository();
    }

}

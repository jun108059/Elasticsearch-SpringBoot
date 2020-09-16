package com.searchengine.yjpark.config.elasticsearch;

import com.searchengine.yjpark.es.client.ElasticsearchClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ESConfig {
    // About ElasticsearchConfig
    @Value("${elasticsearch.host}")
    private String elasticHost;

    @Value("${elasticsearch.port}")
    private int elasticPort;

    @Bean
    ElasticsearchClient elasticsearchClient() {
        return new ElasticsearchClient(elasticHost, elasticPort);
    }

}

package com.searchengine.yjpark.repository;

import com.searchengine.yjpark.domain.DataBaseInfo;
import com.searchengine.yjpark.domain.Service;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;


import javax.sql.DataSource;
import java.util.*;

public class JdbcTemplateBulkRepository implements BulkRepository {

    /**
     * 데이터베이스 정보 동적을 연결
     * @param dbInfo
     * @param serviceInfo
     * @return
     */
    @Override
    public List<Map<String, Object>> dynamicMapping (List<DataBaseInfo> dbInfo, com.searchengine.yjpark.domain.Service serviceInfo) {
        String dbConnIp = dbInfo.get(0).getDbConnIp();
        String dbId = dbInfo.get(0).getDbId();
        String dbPw = dbInfo.get(0).getDbPw();
        String bulkQuery = serviceInfo.getBulkQuery(); // Ex) SELECT * FROM curation

        BasicDataSource dataSources = new BasicDataSource();

        String url = "jdbc:mysql://" + dbConnIp + "?serverTimezone=UTC&characterEncoding=UTF-8";

        dataSources.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSources.setUrl(url);
        dataSources.setUsername(dbId);
        dataSources.setPassword(dbPw);

        // datasource 연결하기
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSources);
        // Map List 반환
        return jdbcTemplate.queryForList(bulkQuery);
    }

    /**
     * Test Code - Curation Row 개수 출력
     * @param dbInfo
     * @param serviceInfo
     * @return
     */
    @Override
    public int countCuration (List<DataBaseInfo> dbInfo, com.searchengine.yjpark.domain.Service serviceInfo) {
        String dbConnIp = dbInfo.get(0).getDbConnIp();
        String dbId = dbInfo.get(0).getDbId();
        String dbPw = dbInfo.get(0).getDbPw();

        BasicDataSource dataSource1 = new BasicDataSource();

        String url = "jdbc:mysql://" + dbConnIp + "?serverTimezone=UTC&characterEncoding=UTF-8";

        dataSource1.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource1.setUrl(url);
        dataSource1.setUsername(dbId);
        dataSource1.setPassword(dbPw);

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource1);

        String sql = "SELECT count(*) FROM curation";
        int rowCount = jdbcTemplate.queryForObject(sql, Integer.class);

        return rowCount;
    }
}

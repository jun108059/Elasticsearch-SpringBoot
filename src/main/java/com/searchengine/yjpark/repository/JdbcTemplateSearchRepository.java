package com.searchengine.yjpark.repository;

import com.searchengine.yjpark.domain.DataBaseInfo;
import com.searchengine.yjpark.domain.Service;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

public class JdbcTemplateSearchRepository implements SearchRepository {

    /**
     * Test Code - Curation Row 개수 출력
     * @param dbInfo
     * @param serviceInfo
     * @return
     */
    @Override
    public int countDocument (List<DataBaseInfo> dbInfo, Service serviceInfo) {
        String dbConnIp = dbInfo.get(0).getDbConnIp();
        String dbId = dbInfo.get(0).getDbId();
        String dbPw = dbInfo.get(0).getDbPw();
        String idColumn = serviceInfo.getIdColumn(); // table 명

        BasicDataSource dataSource1 = new BasicDataSource();

        String url = "jdbc:mysql://" + dbConnIp + "?serverTimezone=UTC&characterEncoding=UTF-8";

        dataSource1.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource1.setUrl(url);
        dataSource1.setUsername(dbId);
        dataSource1.setPassword(dbPw);

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource1);

        String sql = "SELECT count(*) FROM " + idColumn;
        int rowCount = jdbcTemplate.queryForObject(sql, Integer.class);

        return rowCount;
    }
}

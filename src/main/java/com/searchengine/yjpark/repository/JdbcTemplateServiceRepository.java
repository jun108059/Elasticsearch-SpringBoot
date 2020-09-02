package com.searchengine.yjpark.repository;

import com.searchengine.yjpark.domain.DataBaseInfo;
import com.searchengine.yjpark.domain.Service;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JdbcTemplateServiceRepository implements ServiceRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateServiceRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public DataBaseInfo save(DataBaseInfo dataBaseInfo) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("database_list").usingGeneratedKeyColumns("idx");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("db_conn_ip", dataBaseInfo.getDbConnIp());
        parameters.put("db_id", dataBaseInfo.getDbId());
        parameters.put("db_pw", dataBaseInfo.getDbPw());

        Number key = jdbcInsert.executeAndReturnKey(new
                MapSqlParameterSource(parameters));
        dataBaseInfo.setId(key.longValue());
        return dataBaseInfo;
    }

    @Override
    public Optional<DataBaseInfo> findByHost(String host) {
        List<DataBaseInfo> result = jdbcTemplate.query("SELECT * FROM database_list WHERE db_conn_ip = ?", databaseRowMapper(), host);
        return result.stream().findAny(); // Optional로 반환하기 위해
    }

    @Override
    public List<DataBaseInfo> findAll() {
        return jdbcTemplate.query("SELECT * FROM database_list", databaseRowMapper());
    }

    private RowMapper<DataBaseInfo> databaseRowMapper() {
        return new RowMapper<DataBaseInfo>() {
            @Override
            public DataBaseInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
                DataBaseInfo dataBaseInfo = new DataBaseInfo();
                dataBaseInfo.setDbConnIp(rs.getString("db_conn_ip"));
                dataBaseInfo.setDbId(rs.getString("db_id"));
                dataBaseInfo.setDbPw(rs.getString("db_pw"));
                return dataBaseInfo;
            }
        };
    }

    @Override
    public Service saveService(Service service) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("service").usingGeneratedKeyColumns("idx");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("service_id", service.getServiceId());
        parameters.put("service_detail", service.getServiceDetail());
        parameters.put("bulk_query", service.getBulkQuery());
        parameters.put("db_conn_ip", service.getDbInfo());

        Number key = jdbcInsert.executeAndReturnKey(new
                MapSqlParameterSource(parameters));
        service.setId(key.longValue());
        return service;
    }
}

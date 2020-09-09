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

public class JdbcTemplateBulkRepository implements BulkRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateBulkRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public DataBaseInfo save(DataBaseInfo dataBaseInfo) {
        // 컨텐츠 생성!
        // Bulk - 선택한 DB(127.0.0.1:8080/테이블명)정보의 ES 데이터 생성
        // 사용자로부터 입력받는 것?
        // @서비스 값(그냥 ES 데이터(인덱스 이름))
        // @
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

    @Override
    public List<Service> findAllService() {
        return jdbcTemplate.query("SELECT * FROM service", serviceRowMapper());
    }

    private RowMapper<Service> serviceRowMapper() {
        return new RowMapper<Service>() {
            @Override
            public Service mapRow(ResultSet rs, int rowNum) throws SQLException {
                Service service = new Service();
                service.setServiceId(rs.getString("service_id"));
                service.setServiceDetail(rs.getString("service_detail"));
                service.setBulkQuery(rs.getString("bulk_query"));
                service.setDbInfo(rs.getString("db_conn_ip"));
                return service;
            }
        };
    }
}

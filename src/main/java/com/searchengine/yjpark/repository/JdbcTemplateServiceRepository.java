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

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("db_conn_ip", dataBaseInfo.getDbConnIp());
        parameters.put("db_id", dataBaseInfo.getDbId());
        parameters.put("db_pw", dataBaseInfo.getDbPw());

        Number key = jdbcInsert.executeAndReturnKey(new
                MapSqlParameterSource(parameters));
        dataBaseInfo.setIdx(key.longValue());
        return dataBaseInfo;
    }

    @Override
    public Optional<DataBaseInfo> findByHost(String host) {
        List<DataBaseInfo> result = jdbcTemplate.query("SELECT * FROM database_list WHERE db_conn_ip = ?", databaseRowMapper(), host);
        return result.stream().findAny(); // Optional로 반환하기 위해
    }

    @Override
    public List<DataBaseInfo> findDbByIdx(Long dbIdx) {
        List<DataBaseInfo> result = jdbcTemplate.query("SELECT * FROM database_list WHERE idx = ?", databaseRowMapper(), dbIdx);
        return result;
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
                dataBaseInfo.setIdx(rs.getLong("idx"));
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
        parameters.put("db_idx", service.getDbIdx());
        parameters.put("bulk_query", service.getBulkQuery());
        parameters.put("id_column", service.getIdColumn());

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
                service.setDbIdx(rs.getLong("db_idx"));
                service.setIdColumn(rs.getString("id_column"));
                return service;
            }
        };
    }

    @Override
    public Service findServiceById(String id) {
        String sql = "SELECT * FROM service WHERE service_id = ?";

        return (Service) jdbcTemplate.queryForObject(sql, serviceRowMapper(), id);
    }

    @Override
    public String findBulkQuery(String id) {
        String sql = "SELECT bulk_query FROM service WHERE service_id = ?";

        String bulkQuery = (String) jdbcTemplate.queryForObject(sql, new Object[] { id }, String.class);
        return bulkQuery;
    }

    @Override
    public int findDbIdx(String id) {
        String sql = "SELECT db_idx FROM service WHERE service_id = ?";

        int dbIdx = (int) jdbcTemplate.queryForObject(sql, new Object[] { id }, Integer.class);

        return dbIdx;
    }
}

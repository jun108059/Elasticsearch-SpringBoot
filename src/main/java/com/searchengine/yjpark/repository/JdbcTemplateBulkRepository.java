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
    public void bulk(DataBaseInfo dataBaseInfo, Service service) {
        // bulk Index 생성
        // Bulk - 선택한 DB(127.0.0.1:8080/테이블명)정보의 ES 데이터 생성
        // 사용자로부터 입력받는 것?
        // @서비스 값(그냥 ES 데이터(인덱스 이름))
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("database_list").usingGeneratedKeyColumns("idx");

        Map<String, Object> parameters = new HashMap<>();

        parameters.put("db_conn_ip", dataBaseInfo.getDbConnIp());
        parameters.put("db_id", dataBaseInfo.getDbId());
        parameters.put("db_pw", dataBaseInfo.getDbPw());

        Number key = jdbcInsert.executeAndReturnKey(new
                MapSqlParameterSource(parameters));
        dataBaseInfo.setIdx(key.longValue());

    }

}

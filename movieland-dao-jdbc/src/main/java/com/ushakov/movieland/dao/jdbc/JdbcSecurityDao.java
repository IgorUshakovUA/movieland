package com.ushakov.movieland.dao.jdbc;

import com.ushakov.movieland.common.Credentials;
import com.ushakov.movieland.common.SecurityToken;
import com.ushakov.movieland.dao.SecurityDao;
import com.ushakov.movieland.dao.jdbc.mapper.SecurityTokenRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcSecurityDao implements SecurityDao {
    private static final String LOGON_SQL="SELECT nickName FROM app_user WHERE UPPER(email) = UPPER(?) AND password = MD5(CONCAT(?,salt))";
    private static final SecurityTokenRowMapper SECURITY_TOKEN_ROW_MAPPER = new SecurityTokenRowMapper();

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcSecurityDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public SecurityToken logon(Credentials credentials) {
        return jdbcTemplate.queryForObject(LOGON_SQL, SECURITY_TOKEN_ROW_MAPPER, credentials.getEmail(), credentials.getPassword());
    }
}

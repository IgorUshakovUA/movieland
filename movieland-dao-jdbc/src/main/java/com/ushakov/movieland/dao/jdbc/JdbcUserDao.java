package com.ushakov.movieland.dao.jdbc;

import com.ushakov.movieland.dao.UserDao;
import com.ushakov.movieland.dao.jdbc.mapper.UserRowMapper;
import com.ushakov.movieland.entity.User;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(isolation = Isolation.READ_COMMITTED)
public class JdbcUserDao implements UserDao {
    private static final String GET_USER_BY_ID_SQL = "SELECT id, nickname FROM app_user WHERE id = ?";
    private static final UserRowMapper USER_ROW_MAPPER = new UserRowMapper();
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcUserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User getUserById(int id) {
        User user = jdbcTemplate.queryForObject(GET_USER_BY_ID_SQL, USER_ROW_MAPPER, id);

        logger.debug("User: {}", user);

        return user;
    }
}

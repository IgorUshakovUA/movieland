package com.ushakov.movieland.dao.jdbc;

import com.ushakov.movieland.dao.UserDao;
import com.ushakov.movieland.dao.jdbc.mapper.UserRowMapper;
import com.ushakov.movieland.entity.User;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JdbcUserDaoTest {

    @Test
    public void testGetUserById() {
        // Prepare
        JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);

        User expectedUser = new User(1,"my nick name");

        // When
        when(jdbcTemplate.queryForObject(any(String.class),any(UserRowMapper.class),any(Integer.class))).thenReturn(expectedUser);

        // Then
        UserDao userDao = new JdbcUserDao(jdbcTemplate);
        User actualUser = userDao.getUserById(1);

        assertEquals(expectedUser, actualUser);
    }

}
package com.ushakov.movieland.dao.jdbc;

import com.ushakov.movieland.common.Credentials;
import com.ushakov.movieland.common.SecurityToken;
import com.ushakov.movieland.common.UserRole;
import com.ushakov.movieland.dao.SecurityDao;
import com.ushakov.movieland.dao.jdbc.mapper.SecurityTokenRowMapper;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JdbcSecurityDaoTest {

    @Test
    public void testLogon() {
        // Prepare
        JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);

        SecurityToken expectedSecurityTocken = new SecurityToken();
        expectedSecurityTocken.setUuid(UUID.randomUUID().toString());
        expectedSecurityTocken.setNickName("my nick name");
        expectedSecurityTocken.setUserRole(UserRole.USER);
        expectedSecurityTocken.setId(1);

        Credentials credentials = new Credentials("my@email.com", "my_password");

        // When
        when(jdbcTemplate.queryForObject(any(String.class), any(SecurityTokenRowMapper.class), any(String.class), any(String.class))).thenReturn(expectedSecurityTocken);

        // Then
        SecurityDao securityDao = new JdbcSecurityDao(jdbcTemplate);
        SecurityToken actualSecurityTocken = securityDao.logon(credentials);

        assertEquals(expectedSecurityTocken, actualSecurityTocken);
    }

}
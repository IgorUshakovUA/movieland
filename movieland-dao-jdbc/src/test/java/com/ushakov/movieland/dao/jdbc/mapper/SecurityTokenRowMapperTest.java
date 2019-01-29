package com.ushakov.movieland.dao.jdbc.mapper;

import com.ushakov.movieland.common.SecurityToken;
import com.ushakov.movieland.common.UserRole;
import org.junit.Test;

import java.sql.ResultSet;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SecurityTokenRowMapperTest {

    @Test
    public void testMapRow() throws Exception {
        // Prepare
        ResultSet resultSet = mock(ResultSet.class);

        // When
        when(resultSet.getString("nickName")).thenReturn("my nick name");
        when(resultSet.getString("userRole")).thenReturn("USER");
        when(resultSet.getInt("id")).thenReturn(1);

        // Then
        SecurityTokenRowMapper rowMapper = new SecurityTokenRowMapper();
        SecurityToken securityToken = rowMapper.mapRow(resultSet, 1);

        assertEquals("my nick name", securityToken.getNickName());
        assertEquals(UserRole.USER, securityToken.getUserRole());
        assertEquals(1, securityToken.getUser().getId());
        assertNotNull(securityToken.getUuid());
    }

}
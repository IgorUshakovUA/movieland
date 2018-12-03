package com.ushakov.movieland.dao.jdbc.mapper;

import com.ushakov.movieland.common.SecurityToken;
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

        // Then
        SecurityTokenRowMapper rowMapper = new SecurityTokenRowMapper();
        SecurityToken securityToken = rowMapper.mapRow(resultSet, 1);

        assertEquals("my nick name", securityToken.getNickName());
        assertNotNull(securityToken.getUuid());
    }

}
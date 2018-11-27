package com.ushakov.movieland.dao.jdbc.mapper;

import com.ushakov.movieland.entity.User;
import org.junit.Test;

import java.sql.ResultSet;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserRowMapperTest {

    @Test
    public void testMapRow() throws Exception {
        // Prepare
        ResultSet resultSet = mock(ResultSet.class);

        // When
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("nickname")).thenReturn("my nick name");

        // Then
        UserRowMapper rowMapper = new UserRowMapper();
        User user = rowMapper.mapRow(resultSet, 1);

        assertEquals(1, user.getId());
        assertEquals("my nick name", user.getNickname());
    }

}
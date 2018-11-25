package com.ushakov.movieland.dao.jdbc.mapper;

import com.ushakov.movieland.entity.Country;
import org.junit.Test;

import java.sql.ResultSet;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CountryRowMapperTest {

    @Test
    public void testMapRow() throws Exception {
        // Prepare
        ResultSet resultSet = mock(ResultSet.class);

        // When
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("name")).thenReturn("США");

        // Then
        CountryRowMapper rowMapper = new CountryRowMapper();
        Country country = rowMapper.mapRow(resultSet, 1);

        assertEquals(1, country.getId());
        assertEquals("США", country.getName());
    }

}
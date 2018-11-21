package com.ushakov.movieland.dao.jdbc.mapper;

import com.ushakov.movieland.entity.Genre;
import org.junit.Test;

import java.sql.ResultSet;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GenreRowMapperTest {
    @Test
    public void testRowMap() throws Exception {
        // Prepare
        ResultSet resultSet = mock(ResultSet.class);

        // When
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("name")).thenReturn("драма");

        // Then
        GenreRowMapper mapper = new GenreRowMapper();
        Genre genre = mapper.mapRow(resultSet, 0);

        assertEquals(1, genre.getId());
        assertEquals("драма", genre.getName());

    }
}
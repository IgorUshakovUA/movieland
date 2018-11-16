package com.ushakov.movieland.dao.jdbc.mapper;

import com.ushakov.movieland.entity.Movie;
import org.junit.Test;

import java.sql.ResultSet;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MovieRowMapperTest {

    @Test
    public void testRowMap() throws Exception {
        // Prepare
        ResultSet resultSet = mock(ResultSet.class);

        // When
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("nameRussian")).thenReturn("Побег из Шоушенка");
        when(resultSet.getString("nameNative")).thenReturn("The Shawshank Redemption");
        when(resultSet.getInt("yearOfRelease")).thenReturn(1994);
        when(resultSet.getDouble("rating")).thenReturn(8.9);
        when(resultSet.getDouble("price")).thenReturn(123.45);
        when(resultSet.getString("picturePath")).thenReturn("path1");

        // Then
        MovieRowMapper mapper = new MovieRowMapper();
        Movie movie = mapper.mapRow(resultSet, 0);

        assertEquals(1, movie.getId());
        assertEquals("Побег из Шоушенка", movie.getNameRussian());
        assertEquals("The Shawshank Redemption", movie.getNameNative());
        assertEquals(1994, movie.getYearOfRelease());
        assertEquals(8.9, movie.getRating(), 1e-3);
        assertEquals(123.45, movie.getPrice(), 1e-3);
        assertEquals("path1", movie.getPicturePath());
    }

}
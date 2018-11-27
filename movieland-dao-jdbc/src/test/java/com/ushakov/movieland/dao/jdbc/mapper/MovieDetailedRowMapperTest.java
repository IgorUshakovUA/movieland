package com.ushakov.movieland.dao.jdbc.mapper;

import com.ushakov.movieland.entity.*;
import org.junit.Test;

import java.sql.ResultSet;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MovieDetailedRowMapperTest {

    @Test
    public void testMapRow() throws Exception {
        // Prepare
        ResultSet resultSet = mock(ResultSet.class);

        MovieDetailed expectedMovieDetailed = new MovieDetailed();
        expectedMovieDetailed.setId(1);
        expectedMovieDetailed.setNameRussian("nameRussian1");
        expectedMovieDetailed.setNameNative("nameNative1");
        expectedMovieDetailed.setYearOfRelease(1999);
        expectedMovieDetailed.setDescritpion("description1");
        expectedMovieDetailed.setRating(8.5);
        expectedMovieDetailed.setPrice(99.99);
        expectedMovieDetailed.setPicturePath("picturePath1");

        // When
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("nameRussian")).thenReturn("nameRussian1");
        when(resultSet.getString("nameNative")).thenReturn("nameNative1");
        when(resultSet.getInt("yearOfRelease")).thenReturn(1999);
        when(resultSet.getString("description")).thenReturn("description1");
        when(resultSet.getDouble("rating")).thenReturn(8.5);
        when(resultSet.getDouble("price")).thenReturn(99.99);
        when(resultSet.getString("picturePath")).thenReturn("picturePath1");

        // Then
        MovieDetailedRowMapper rowMapper = new MovieDetailedRowMapper();
        MovieDetailed actualMovieDetailed = rowMapper.mapRow(resultSet, 1);

        assertEquals(expectedMovieDetailed, actualMovieDetailed);
    }

}
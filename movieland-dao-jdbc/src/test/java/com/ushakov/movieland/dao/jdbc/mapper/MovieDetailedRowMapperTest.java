package com.ushakov.movieland.dao.jdbc.mapper;

import com.ushakov.movieland.dao.CountryDao;
import com.ushakov.movieland.dao.GenreDao;
import com.ushakov.movieland.dao.ReviewDao;
import com.ushakov.movieland.entity.*;
import org.junit.Test;

import java.sql.ResultSet;
import java.util.Arrays;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MovieDetailedRowMapperTest {

    @Test
    public void testMapRow() throws Exception {
        // Prepare
        GenreDao genreDao = mock(GenreDao.class);
        CountryDao countryDao = mock(CountryDao.class);
        ReviewDao reviewDao = mock(ReviewDao.class);
        ResultSet resultSet = mock(ResultSet.class);

        Genre genre = new Genre(1, "genre1");

        Country country = new Country(1, "country1");

        User user = new User(1, "nickname1");

        Review review = new Review(1, user, "some text");

        MovieDetailed expectedMovieDetailed = new MovieDetailed();
        expectedMovieDetailed.setId(1);
        expectedMovieDetailed.setNameRussian("nameRussian1");
        expectedMovieDetailed.setNameNative("nameNative1");
        expectedMovieDetailed.setYearOfRelease(1999);
        expectedMovieDetailed.setDescritpion("description1");
        expectedMovieDetailed.setRating(8.5);
        expectedMovieDetailed.setPrice(99.99);
        expectedMovieDetailed.setPicturePath("picturePath1");
        expectedMovieDetailed.setCountries(Arrays.asList(country));
        expectedMovieDetailed.setGenres(Arrays.asList(genre));
        expectedMovieDetailed.setReviews(Arrays.asList(review));

        // When
        when(genreDao.getGenresGenreGroupId(any(Integer.class))).thenReturn(Arrays.asList(genre));
        when(countryDao.getCountriesByCountryGroupId(any(Integer.class))).thenReturn(Arrays.asList(country));
        when(reviewDao.getReviewesByMovieId(any(Integer.class))).thenReturn(Arrays.asList(review));
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("nameRussian")).thenReturn("nameRussian1");
        when(resultSet.getString("nameNative")).thenReturn("nameNative1");
        when(resultSet.getInt("yearOfRelease")).thenReturn(1999);
        when(resultSet.getString("description")).thenReturn("description1");
        when(resultSet.getDouble("rating")).thenReturn(8.5);
        when(resultSet.getDouble("price")).thenReturn(99.99);
        when(resultSet.getString("picturePath")).thenReturn("picturePath1");

        // Then
        MovieDetailedRowMapper rowMapper = new MovieDetailedRowMapper(countryDao, genreDao, reviewDao);
        MovieDetailed actualMovieDetailed = rowMapper.mapRow(resultSet, 1);

        assertEquals(expectedMovieDetailed, actualMovieDetailed);
    }

}
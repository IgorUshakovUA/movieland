package com.ushakov.movieland.dao.jdbc;

import com.ushakov.movieland.dao.MovieDao;
import com.ushakov.movieland.dao.jdbc.mapper.MovieRowMapper;
import com.ushakov.movieland.entity.Movie;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JdbcMovieDaoTest {

    @Test
    public void testGetAll() {
        JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);

        List<Movie> expectedMovieList = new ArrayList<>();

        Movie movie1 = new Movie();
        movie1.setId(1);
        movie1.setNameRussian("Побег из Шоушенка");
        movie1.setNameNative("The Shawshank Redemption");
        movie1.setYearOfRelease(1994);
        movie1.setRating(8.9);
        movie1.setPrice(123.45);
        movie1.setPicturePath("path1");
        expectedMovieList.add(movie1);

        Movie movie2 = new Movie();
        movie2.setId(2);
        movie2.setNameRussian("Зеленая миля");
        movie2.setNameNative("The Green Mile");
        movie2.setYearOfRelease(1999);
        movie2.setRating(8.9);
        movie2.setPrice(134.67);
        movie2.setPicturePath("path2");
        expectedMovieList.add(movie2);

        Movie movie3 = new Movie();
        movie3.setId(3);
        movie3.setNameRussian("Форрест Гамп");
        movie3.setNameNative("Forrest Gump");
        movie3.setYearOfRelease(1994);
        movie3.setRating(8.6);
        movie3.setPrice(200.60);
        movie3.setPicturePath("path3");
        expectedMovieList.add(movie3);

        MovieDao movieDao = new JdbcMovieDao(jdbcTemplate);

        // When
        when(jdbcTemplate.query(any(String.class), any(MovieRowMapper.class))).thenReturn(expectedMovieList);

        // Then
        List<Movie> actualMovieList = movieDao.getAll();

        for (Movie actualMovie : actualMovieList) {
            assertTrue(expectedMovieList.indexOf(actualMovie) > -1);
        }

        assertEquals(3, expectedMovieList.size());
    }

    @Test
    public void testGetThreeRandomMovies() {
        JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);

        List<Movie> expectedMovieList = new ArrayList<>();

        Movie movie1 = new Movie();
        movie1.setId(1);
        movie1.setNameRussian("Побег из Шоушенка");
        movie1.setNameNative("The Shawshank Redemption");
        movie1.setYearOfRelease(1994);
        movie1.setRating(8.9);
        movie1.setPrice(123.45);
        movie1.setPicturePath("path1");
        expectedMovieList.add(movie1);

        Movie movie2 = new Movie();
        movie2.setId(2);
        movie2.setNameRussian("Зеленая миля");
        movie2.setNameNative("The Green Mile");
        movie2.setYearOfRelease(1999);
        movie2.setRating(8.9);
        movie2.setPrice(134.67);
        movie2.setPicturePath("path2");
        expectedMovieList.add(movie2);

        Movie movie3 = new Movie();
        movie3.setId(3);
        movie3.setNameRussian("Форрест Гамп");
        movie3.setNameNative("Forrest Gump");
        movie3.setYearOfRelease(1994);
        movie3.setRating(8.6);
        movie3.setPrice(200.60);
        movie3.setPicturePath("path3");
        expectedMovieList.add(movie3);

        MovieDao movieDao = new JdbcMovieDao(jdbcTemplate);

        // When
        when(jdbcTemplate.query(any(String.class), any(MovieRowMapper.class))).thenReturn(expectedMovieList);

        // Then
        List<Movie> actualMovieList = movieDao.getThreeRandomMovies();

        for (Movie actualMovie : actualMovieList) {
            assertTrue(expectedMovieList.indexOf(actualMovie) > -1);
        }

        assertEquals(3, expectedMovieList.size());
    }

    @Test
    public void testGetMoviesByGenre() {
        JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);

        List<Movie> expectedMovieList = new ArrayList<>();

        Movie movie1 = new Movie();
        movie1.setId(1);
        movie1.setNameRussian("Побег из Шоушенка");
        movie1.setNameNative("The Shawshank Redemption");
        movie1.setYearOfRelease(1994);
        movie1.setRating(8.9);
        movie1.setPrice(123.45);
        movie1.setPicturePath("path1");
        expectedMovieList.add(movie1);

        Movie movie2 = new Movie();
        movie2.setId(2);
        movie2.setNameRussian("Зеленая миля");
        movie2.setNameNative("The Green Mile");
        movie2.setYearOfRelease(1999);
        movie2.setRating(8.9);
        movie2.setPrice(134.67);
        movie2.setPicturePath("path2");
        expectedMovieList.add(movie2);

        Movie movie3 = new Movie();
        movie3.setId(3);
        movie3.setNameRussian("Форрест Гамп");
        movie3.setNameNative("Forrest Gump");
        movie3.setYearOfRelease(1994);
        movie3.setRating(8.6);
        movie3.setPrice(200.60);
        movie3.setPicturePath("path3");
        expectedMovieList.add(movie3);

        MovieDao movieDao = new JdbcMovieDao(jdbcTemplate);

        // When
        when(jdbcTemplate.query(any(String.class), any(MovieRowMapper.class), any(Integer.class))).thenReturn(expectedMovieList);

        // Then
        List<Movie> actualMovieList = movieDao.getMoviesByGenre(1);

        for (Movie actualMovie : actualMovieList) {
            assertTrue(expectedMovieList.indexOf(actualMovie) > -1);
        }

        assertEquals(3, expectedMovieList.size());
    }
}
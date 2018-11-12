package com.ushakov.movieland.dao.jdbc;

import com.ushakov.movieland.dao.MovieDao;
import com.ushakov.movieland.dao.jdbc.mapper.MovieRowMapper;
import com.ushakov.movieland.entity.Movie;
import com.ushakov.movieland.util.TestUtil;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JdbcMovieDaoTest {

    @Test
    public void testGetAll() {
        JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);

        List<Movie> expectedMovieList = TestUtil.makeMovieList();

        MovieDao movieDao = new JdbcMovieDao(jdbcTemplate);

        // When
        when(jdbcTemplate.query(any(String.class), any(MovieRowMapper.class))).thenReturn(expectedMovieList);

        // Then
        List<Movie> actualMovieList = movieDao.getAll();

        TestUtil.assertEqualsForMovieList(expectedMovieList, actualMovieList);
    }

    @Test
    public void testGetThreeRandomMovies() {
        JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);

        List<Movie> expectedMovieList = TestUtil.makeMovieList();

        MovieDao movieDao = new JdbcMovieDao(jdbcTemplate);

        // When
        when(jdbcTemplate.query(any(String.class), any(MovieRowMapper.class))).thenReturn(expectedMovieList);

        // Then
        List<Movie> actualMovieList = movieDao.getThreeRandomMovies();

        TestUtil.assertEqualsForMovieList(expectedMovieList, actualMovieList);
    }
}
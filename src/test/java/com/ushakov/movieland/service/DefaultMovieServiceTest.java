package com.ushakov.movieland.service;

import com.ushakov.movieland.dao.MovieDao;
import com.ushakov.movieland.entity.Movie;
import com.ushakov.movieland.util.TestUtil;
import org.junit.Test;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DefaultMovieServiceTest {

    @Test
    public void testGetAll() throws Exception {
        // Prepare
        MovieDao movieDao = mock(MovieDao.class);

        List<Movie> expectedMovieList = TestUtil.makeMovieList();

        MovieService movieService = new DefaultMovieService(movieDao);

        // When
        when(movieDao.getAll()).thenReturn(expectedMovieList);

        //Then
        List<Movie> actualMovieList = movieService.getAll();

        TestUtil.assertEqualsForMovieList(expectedMovieList, actualMovieList);
    }

    @Test
    public void testGetThreeRandomMovies() throws Exception {
        // Prepare
        MovieDao movieDao = mock(MovieDao.class);

        List<Movie> expectedMovieList = TestUtil.makeMovieList();

        MovieService movieService = new DefaultMovieService(movieDao);

        // When
        when(movieDao.getThreeRandomMovies()).thenReturn(expectedMovieList);

        //Then
        List<Movie> actualMovieList = movieService.getThreeRandomMovies();

        TestUtil.assertEqualsForMovieList(expectedMovieList, actualMovieList);
    }
}
package com.ushakov.movieland.service;

import com.ushakov.movieland.dao.MovieDao;
import com.ushakov.movieland.entity.Movie;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DefaultMovieServiceTest {

    @Test
    public void testGetAll() throws Exception {
        MovieDao movieDao = mock(MovieDao.class);

        List<Movie> expectedMovieList = new ArrayList();

        Movie movie = new Movie();
        movie.setId(1);
        movie.setNameRussian("Побег из Шоушенка");
        movie.setNameNative("The Shawshank Redemption");
        movie.setYearOfRelease(1994);
        movie.setRating(8.9);
        movie.setPrice(123.45);
        movie.setPicturePath("path1");
        expectedMovieList.add(movie);

        movie = new Movie();
        movie.setId(2);
        movie.setNameRussian("Зеленая миля");
        movie.setNameNative("The Green Mile");
        movie.setYearOfRelease(1999);
        movie.setRating(8.9);
        movie.setPrice(134.67);
        movie.setPicturePath("path2");
        expectedMovieList.add(movie);

        movie = new Movie();
        movie.setId(3);
        movie.setNameRussian("Форрест Гамп");
        movie.setNameNative("Forrest Gump");
        movie.setYearOfRelease(1994);
        movie.setRating(8.6);
        movie.setPrice(200.60);
        movie.setPicturePath("path3");
        expectedMovieList.add(movie);

        when(movieDao.getAll()).thenReturn(expectedMovieList);

        MovieService movieService = new DefaultMovieService(movieDao);

        List<Movie> actualMovieList = movieService.getAll();

        assertEquals(3, actualMovieList.size());
        assertEquals(expectedMovieList.get(0).toString(), actualMovieList.get(0).toString());
        assertEquals(expectedMovieList.get(1).toString(), actualMovieList.get(1).toString());
        assertEquals(expectedMovieList.get(2).toString(), actualMovieList.get(2).toString());
    }
}
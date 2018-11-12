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
        // Prepare
        MovieDao movieDao = mock(MovieDao.class);

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

        MovieService movieService = new DefaultMovieService(movieDao);

        // When
        when(movieDao.getAll()).thenReturn(expectedMovieList);

        //Then
        List<Movie> actualMovieList = movieService.getAll();

        assertEquals(3, actualMovieList.size());

        Movie actual1 = actualMovieList.get(0);
        Movie expected1 = expectedMovieList.get(0);
        assertEquals(expected1.getId(),actual1.getId());
        assertEquals(expected1.getNameRussian(),actual1.getNameRussian());
        assertEquals(expected1.getNameNative(),actual1.getNameNative());
        assertEquals(expected1.getYearOfRelease(),actual1.getYearOfRelease());
        assertEquals(expected1.getRating(),actual1.getRating(),1e-3);
        assertEquals(expected1.getPrice(),actual1.getPrice(),1e-3);
        assertEquals(expected1.getPicturePath(),actual1.getPicturePath());

        Movie actual2 = actualMovieList.get(1);
        Movie expected2 = expectedMovieList.get(1);
        assertEquals(expected2.getId(),actual2.getId());
        assertEquals(expected2.getNameRussian(),actual2.getNameRussian());
        assertEquals(expected2.getNameNative(),actual2.getNameNative());
        assertEquals(expected2.getYearOfRelease(),actual2.getYearOfRelease());
        assertEquals(expected2.getRating(),actual2.getRating(),1e-3);
        assertEquals(expected2.getPrice(),actual2.getPrice(),1e-3);
        assertEquals(expected2.getPicturePath(),actual2.getPicturePath());

        Movie actual3 = actualMovieList.get(2);
        Movie expected3 = expectedMovieList.get(2);
        assertEquals(expected3.getId(),actual3.getId());
        assertEquals(expected3.getNameRussian(),actual3.getNameRussian());
        assertEquals(expected3.getNameNative(),actual3.getNameNative());
        assertEquals(expected3.getYearOfRelease(),actual3.getYearOfRelease());
        assertEquals(expected3.getRating(),actual3.getRating(),1e-3);
        assertEquals(expected3.getPrice(),actual3.getPrice(),1e-3);
        assertEquals(expected3.getPicturePath(),actual3.getPicturePath());
    }
}
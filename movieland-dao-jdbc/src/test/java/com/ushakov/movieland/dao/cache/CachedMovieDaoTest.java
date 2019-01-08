package com.ushakov.movieland.dao.cache;

import com.ushakov.movieland.dao.MovieDao;
import com.ushakov.movieland.entity.MovieDetailed;
import com.ushakov.movieland.entity.NewMovie;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CachedMovieDaoTest {

    @Test
    public void testGetMovieByIdInsertUpdate() throws Exception {
        // Preparation
        MovieDao movieDao = mock(MovieDao.class);

        MovieDao chachedMovieDao = new CachedMovieDao(movieDao);

        MovieDetailed movie1 = new MovieDetailed();
        movie1.setId(1);
        movie1.setNameRussian("name russian");
        movie1.setNameNative("name native");
        movie1.setPrice(99.99);
        movie1.setYearOfRelease(1999);
        movie1.setRating(8.1);
        movie1.setPicturePath("picture path");
        movie1.setDescription("description");
        movie1.setReviews(null);
        movie1.setCountries(null);
        movie1.setReviews(null);

        MovieDetailed movie2 = new MovieDetailed();
        movie2.setId(2);
        movie2.setNameRussian("name russian");
        movie2.setNameNative("name native");
        movie2.setPrice(99.99);
        movie2.setYearOfRelease(1999);
        movie2.setRating(8.1);
        movie2.setPicturePath("picture path");
        movie2.setDescription("description");
        movie2.setReviews(null);
        movie2.setCountries(null);
        movie2.setReviews(null);

        MovieDetailed movie3 = new MovieDetailed();
        movie3.setId(3);
        movie3.setNameRussian("name russian");
        movie3.setNameNative("name native");
        movie3.setPrice(99.99);
        movie3.setYearOfRelease(1999);
        movie3.setRating(8.1);
        movie3.setPicturePath("picture path");
        movie3.setDescription("description");
        movie3.setReviews(null);
        movie3.setCountries(null);
        movie3.setReviews(null);

        MovieDetailed movie4 = new MovieDetailed();
        movie4.setId(4);
        movie4.setNameRussian("name russian");
        movie4.setNameNative("name native");
        movie4.setPrice(99.99);
        movie4.setYearOfRelease(1999);
        movie4.setRating(8.1);
        movie4.setPicturePath("picture path");
        movie4.setDescription("description");
        movie4.setReviews(null);
        movie4.setCountries(null);
        movie4.setReviews(null);

        MovieDetailed movie5 = new MovieDetailed();
        movie5.setId(5);
        movie5.setNameRussian("name russian");
        movie5.setNameNative("name native");
        movie5.setPrice(99.99);
        movie5.setYearOfRelease(1999);
        movie5.setRating(8.1);
        movie5.setPicturePath("picture path");
        movie5.setDescription("description");
        movie5.setReviews(null);
        movie5.setCountries(null);
        movie5.setReviews(null);

        MovieDetailed movie6 = new MovieDetailed();
        movie6.setId(6);
        movie6.setNameRussian("name russian");
        movie6.setNameNative("name native");
        movie6.setPrice(99.99);
        movie6.setYearOfRelease(1999);
        movie6.setRating(8.1);
        movie6.setPicturePath("picture path");
        movie6.setDescription("description");
        movie6.setReviews(null);
        movie6.setCountries(null);
        movie6.setReviews(null);

        MovieDetailed movie7 = new MovieDetailed();
        movie7.setId(7);
        movie7.setNameRussian("name russian");
        movie7.setNameNative("name native");
        movie7.setPrice(99.99);
        movie7.setYearOfRelease(1999);
        movie7.setRating(8.1);
        movie7.setPicturePath("picture path");
        movie7.setDescription("description");
        movie7.setReviews(null);
        movie7.setCountries(null);
        movie7.setReviews(null);

        MovieDetailed movie8 = new MovieDetailed();
        movie8.setId(8);
        movie8.setNameRussian("name russian");
        movie8.setNameNative("name native");
        movie8.setPrice(99.99);
        movie8.setYearOfRelease(1999);
        movie8.setRating(8.1);
        movie8.setPicturePath("picture path");
        movie8.setDescription("description");
        movie8.setReviews(null);
        movie8.setCountries(null);
        movie8.setReviews(null);

        MovieDetailed movie9 = new MovieDetailed();
        movie9.setId(9);
        movie9.setNameRussian("name russian");
        movie9.setNameNative("name native");
        movie9.setPrice(99.99);
        movie9.setYearOfRelease(1999);
        movie9.setRating(8.1);
        movie9.setPicturePath("picture path");
        movie9.setDescription("description");
        movie9.setReviews(null);
        movie9.setCountries(null);
        movie9.setReviews(null);

        MovieDetailed movie10 = new MovieDetailed();
        movie10.setId(10);
        movie10.setNameRussian("name russian");
        movie10.setNameNative("name native");
        movie10.setPrice(99.99);
        movie10.setYearOfRelease(1999);
        movie10.setRating(8.1);
        movie10.setPicturePath("picture path");
        movie10.setDescription("description");
        movie10.setReviews(null);
        movie10.setCountries(null);
        movie10.setReviews(null);

        NewMovie newMovie1 = new NewMovie();
        newMovie1.setId(11);
        newMovie1.setNameNative("new name native");
        newMovie1.setNameRussian("new name russian");
        newMovie1.setPicturePath("new picture path");
        newMovie1.setPrice(99.99);
        newMovie1.setYearOfRelease(2001);
        newMovie1.setDescription("new description");
        newMovie1.setCountries(null);
        newMovie1.setGenres(null);

        NewMovie newMovie2 = new NewMovie();
        newMovie2.setId(12);
        newMovie2.setNameNative("new name native");
        newMovie2.setNameRussian("new name russian");
        newMovie2.setPicturePath("new picture path");
        newMovie2.setPrice(99.99);
        newMovie2.setYearOfRelease(2001);
        newMovie2.setDescription("new description");
        newMovie2.setCountries(null);
        newMovie2.setGenres(null);

        NewMovie newMovie3 = new NewMovie();
        newMovie3.setId(13);
        newMovie3.setNameNative("new name native");
        newMovie3.setNameRussian("new name russian");
        newMovie3.setPicturePath("new picture path");
        newMovie3.setPrice(99.99);
        newMovie3.setYearOfRelease(2001);
        newMovie3.setDescription("new description");
        newMovie3.setCountries(null);
        newMovie3.setGenres(null);

        CountDownLatch startGate = new CountDownLatch(1);
        CountDownLatch endGate = new CountDownLatch(1013);

        int expectedCheckSum = (1 + 2 + 3 + 4 + 5 + 6 + 7 + 8 + 9 + 10) * 100 + 11 + 12 + 13 + 99 * 10;

        AtomicInteger actualCheckSum = new AtomicInteger(0);

        class ReadTask extends Thread {
            private int movieId;

            public ReadTask(int movieId) {
                this.movieId = movieId;
            }

            @Override
            public void run() {
                try {
                    startGate.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                MovieDetailed movie = chachedMovieDao.getMovieById(movieId);

                actualCheckSum.addAndGet(movie.getId());

                endGate.countDown();
            }

        }

        class InsertTask extends Thread {
            private NewMovie movie;

            public InsertTask(NewMovie movie) {
                this.movie = movie;
            }

            @Override
            public void run() {
                try {
                    startGate.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                int movieId = chachedMovieDao.insertMovie(movie);

                actualCheckSum.addAndGet(movieId);

                endGate.countDown();
            }
        }

        class UpdateTask extends Thread {
            private NewMovie movie;

            public UpdateTask(int movieId) {
                movie = new NewMovie();
                movie.setId(movieId);
                movie.setGenres(null);
                movie.setCountries(null);
                movie.setDescription("updated description");
                movie.setYearOfRelease(2001);
                movie.setPrice(10.99);
                movie.setNameNative("new name native");
                movie.setNameRussian("new name russian");
            }

            @Override
            public void run() {
                try {
                    startGate.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                int movieId = chachedMovieDao.updateMovie(movie);

                actualCheckSum.addAndGet(movieId);

                endGate.countDown();
            }
        }

        // WHen
        when(movieDao.getMovieById(1)).thenReturn(movie1);
        when(movieDao.getMovieById(2)).thenReturn(movie2);
        when(movieDao.getMovieById(3)).thenReturn(movie3);
        when(movieDao.getMovieById(4)).thenReturn(movie4);
        when(movieDao.getMovieById(5)).thenReturn(movie5);
        when(movieDao.getMovieById(6)).thenReturn(movie6);
        when(movieDao.getMovieById(7)).thenReturn(movie7);
        when(movieDao.getMovieById(8)).thenReturn(movie8);
        when(movieDao.getMovieById(9)).thenReturn(movie9);
        when(movieDao.getMovieById(10)).thenReturn(movie10);
        when(movieDao.insertMovie(newMovie1)).thenReturn(11);
        when(movieDao.insertMovie(newMovie2)).thenReturn(12);
        when(movieDao.insertMovie(newMovie3)).thenReturn(13);
        when(movieDao.updateMovie(any(NewMovie.class))).thenReturn(99);

        // Then
        for (int i = 1; i < 101; i++) {
            for (int j = 1; j < 11; j++) {
                ReadTask task = new ReadTask(j);
                task.start();
            }
        }

        InsertTask insertTask1 = new InsertTask(newMovie1);
        insertTask1.start();
        InsertTask insertTask2 = new InsertTask(newMovie2);
        insertTask2.start();
        InsertTask insertTask3 = new InsertTask(newMovie3);
        insertTask3.start();

        for (int i = 1; i < 11; i++) {
            UpdateTask task = new UpdateTask(i);
            task.start();
        }

        startGate.countDown();

        endGate.await(15, TimeUnit.SECONDS);

        assertEquals(expectedCheckSum, actualCheckSum.get());
    }

}
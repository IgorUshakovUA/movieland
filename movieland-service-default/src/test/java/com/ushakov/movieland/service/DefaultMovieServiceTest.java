package com.ushakov.movieland.service;

import com.ushakov.movieland.common.RequestSearchParam;
import com.ushakov.movieland.dao.MovieDao;
import com.ushakov.movieland.common.SortField;
import com.ushakov.movieland.common.SortType;
import com.ushakov.movieland.entity.Movie;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DefaultMovieServiceTest {

    @Test
    public void testGetAll() throws Exception {
        // Prepare
        MovieDao movieDao = mock(MovieDao.class);
        GenreService genreService = mock(GenreService.class);
        CountryService countryService = mock(CountryService.class);
        ReviewService reviewService = mock(ReviewService.class);
        CurrencyService currencyService = mock(CurrencyService.class);

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

        MovieService movieService = new DefaultMovieService(movieDao, countryService, genreService, reviewService, currencyService, Executors.newCachedThreadPool());

        // When
        when(movieDao.getAll(null)).thenReturn(expectedMovieList);

        //Then
        List<Movie> actualMovieList = movieService.getAll();

        for (Movie actualMovie : actualMovieList) {
            assertTrue(expectedMovieList.indexOf(actualMovie) > -1);
        }

        assertEquals(3, expectedMovieList.size());
    }

    @Test
    public void testGetAllSortedByPriceAsc() throws Exception {
        // Prepare
        MovieDao movieDao = mock(MovieDao.class);
        GenreService genreService = mock(GenreService.class);
        CountryService countryService = mock(CountryService.class);
        ReviewService reviewService = mock(ReviewService.class);
        CurrencyService currencyService = mock(CurrencyService.class);

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

        MovieService movieService = new DefaultMovieService(movieDao, countryService, genreService, reviewService, currencyService, Executors.newCachedThreadPool());

        RequestSearchParam requestSearchParam = new RequestSearchParam();
        requestSearchParam.setSortType(SortType.ASC);
        requestSearchParam.setSortField(SortField.PRICE);

        // When
        when(movieDao.getAll(any(RequestSearchParam.class))).thenReturn(expectedMovieList);

        //Then
        List<Movie> actualMovieList = movieService.getAll(requestSearchParam);

        assertEquals(expectedMovieList.get(0), actualMovieList.get(0));
        assertEquals(expectedMovieList.get(1), actualMovieList.get(1));
        assertEquals(expectedMovieList.get(2), actualMovieList.get(2));

        assertEquals(3, expectedMovieList.size());
    }

    @Test
    public void testGetAllSortedByRatingDesc() throws Exception {
        // Prepare
        MovieDao movieDao = mock(MovieDao.class);
        GenreService genreService = mock(GenreService.class);
        CountryService countryService = mock(CountryService.class);
        ReviewService reviewService = mock(ReviewService.class);
        CurrencyService currencyService = mock(CurrencyService.class);

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
        movie2.setRating(8.3);
        movie2.setPrice(134.67);
        movie2.setPicturePath("path2");
        expectedMovieList.add(movie2);

        Movie movie3 = new Movie();
        movie3.setId(3);
        movie3.setNameRussian("Форрест Гамп");
        movie3.setNameNative("Forrest Gump");
        movie3.setYearOfRelease(1994);
        movie3.setRating(8.1);
        movie3.setPrice(200.60);
        movie3.setPicturePath("path3");
        expectedMovieList.add(movie3);

        MovieService movieService = new DefaultMovieService(movieDao, countryService, genreService, reviewService, currencyService, Executors.newCachedThreadPool());

        RequestSearchParam requestSearchParam = new RequestSearchParam();
        requestSearchParam.setSortType(SortType.DESC);
        requestSearchParam.setSortField(SortField.RATING);

        // When
        when(movieDao.getAll(any(RequestSearchParam.class))).thenReturn(expectedMovieList);

        //Then
        List<Movie> actualMovieList = movieService.getAll(requestSearchParam);

        assertEquals(expectedMovieList.get(0), actualMovieList.get(0));
        assertEquals(expectedMovieList.get(1), actualMovieList.get(1));
        assertEquals(expectedMovieList.get(2), actualMovieList.get(2));

        assertEquals(3, expectedMovieList.size());
    }

    @Test
    public void testGetThreeRandomMovies() throws Exception {
        // Prepare
        MovieDao movieDao = mock(MovieDao.class);
        GenreService genreService = mock(GenreService.class);
        CountryService countryService = mock(CountryService.class);
        ReviewService reviewService = mock(ReviewService.class);
        CurrencyService currencyService = mock(CurrencyService.class);

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

        MovieService movieService = new DefaultMovieService(movieDao, countryService, genreService, reviewService, currencyService, Executors.newCachedThreadPool());

        // When
        when(movieDao.getThreeRandomMovies()).thenReturn(expectedMovieList);

        //Then
        List<Movie> actualMovieList = movieService.getThreeRandomMovies();

        for (Movie actualMovie : actualMovieList) {
            assertTrue(expectedMovieList.indexOf(actualMovie) > -1);
        }

        assertEquals(3, expectedMovieList.size());
    }

    @Test
    public void testGetMoviesByGenre() throws Exception {
        // Prepare
        MovieDao movieDao = mock(MovieDao.class);
        GenreService genreService = mock(GenreService.class);
        CountryService countryService = mock(CountryService.class);
        ReviewService reviewService = mock(ReviewService.class);
        CurrencyService currencyService = mock(CurrencyService.class);

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

        MovieService movieService = new DefaultMovieService(movieDao, countryService, genreService, reviewService, currencyService, Executors.newCachedThreadPool());

        // When
        when(movieDao.getMoviesByGenre(1, null)).thenReturn(expectedMovieList);

        //Then
        List<Movie> actualMovieList = movieService.getMoviesByGenre(1);

        for (Movie actualMovie : actualMovieList) {
            assertTrue(expectedMovieList.indexOf(actualMovie) > -1);
        }

        assertEquals(3, expectedMovieList.size());
    }

    @Test
    public void testGetMoviesByGenreSortedByPriceAsc() throws Exception {
        // Prepare
        MovieDao movieDao = mock(MovieDao.class);
        GenreService genreService = mock(GenreService.class);
        CountryService countryService = mock(CountryService.class);
        ReviewService reviewService = mock(ReviewService.class);
        CurrencyService currencyService = mock(CurrencyService.class);

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

        MovieService movieService = new DefaultMovieService(movieDao, countryService, genreService, reviewService, currencyService, Executors.newCachedThreadPool());

        RequestSearchParam requestSearchParam = new RequestSearchParam();
        requestSearchParam.setSortType(SortType.ASC);
        requestSearchParam.setSortField(SortField.PRICE);

        // When
        when(movieDao.getMoviesByGenre(any(Integer.class), any(RequestSearchParam.class))).thenReturn(expectedMovieList);

        //Then
        List<Movie> actualMovieList = movieService.getMoviesByGenre(1, requestSearchParam);

        assertEquals(expectedMovieList.get(0), actualMovieList.get(0));
        assertEquals(expectedMovieList.get(1), actualMovieList.get(1));
        assertEquals(expectedMovieList.get(2), actualMovieList.get(2));

        assertEquals(3, expectedMovieList.size());
    }

    @Test
    public void testGetMoviesByGenreSortedByRatingDesc() throws Exception {
        // Prepare
        MovieDao movieDao = mock(MovieDao.class);
        GenreService genreService = mock(GenreService.class);
        CountryService countryService = mock(CountryService.class);
        ReviewService reviewService = mock(ReviewService.class);
        CurrencyService currencyService = mock(CurrencyService.class);

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
        movie2.setRating(8.3);
        movie2.setPrice(134.67);
        movie2.setPicturePath("path2");
        expectedMovieList.add(movie2);

        Movie movie3 = new Movie();
        movie3.setId(3);
        movie3.setNameRussian("Форрест Гамп");
        movie3.setNameNative("Forrest Gump");
        movie3.setYearOfRelease(1994);
        movie3.setRating(8.1);
        movie3.setPrice(200.60);
        movie3.setPicturePath("path3");
        expectedMovieList.add(movie3);

        MovieService movieService = new DefaultMovieService(movieDao, countryService, genreService, reviewService, currencyService, Executors.newCachedThreadPool());

        RequestSearchParam requestSearchParam = new RequestSearchParam();
        requestSearchParam.setSortType(SortType.DESC);
        requestSearchParam.setSortField(SortField.RATING);

        // When
        when(movieDao.getMoviesByGenre(any(Integer.class), any(RequestSearchParam.class))).thenReturn(expectedMovieList);

        //Then
        List<Movie> actualMovieList = movieService.getMoviesByGenre(1, requestSearchParam);

        assertEquals(expectedMovieList.get(0), actualMovieList.get(0));
        assertEquals(expectedMovieList.get(1), actualMovieList.get(1));
        assertEquals(expectedMovieList.get(2), actualMovieList.get(2));

        assertEquals(3, expectedMovieList.size());
    }
}
package com.ushakov.movieland.dao.jdbc;

import com.ushakov.movieland.common.RequestSearchParam;
import com.ushakov.movieland.dao.MovieDao;
import com.ushakov.movieland.common.SortField;
import com.ushakov.movieland.common.SortType;
import com.ushakov.movieland.dao.jdbc.mapper.MovieDetailedRowMapper;
import com.ushakov.movieland.dao.jdbc.mapper.MovieRowMapper;
import com.ushakov.movieland.entity.*;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Array;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JdbcMovieDaoTest {
    private static final String INITIAL_SQL = "SELECT * FROM table_name";
    private static final String EXPECTED_SQL_RATING_DESC = "SELECT * FROM table_name ORDER BY rating DESC";
    private static final String EXPECTED_SQL_PRICE_DESC = "SELECT * FROM table_name ORDER BY price DESC";
    private static final String EXPECTED_SQL_PRICE_ASC = "SELECT * FROM table_name ORDER BY price ASC";

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
        List<Movie> actualMovieList = movieDao.getAll(null);

        for (Movie actualMovie : actualMovieList) {
            assertTrue(expectedMovieList.indexOf(actualMovie) > -1);
        }

        assertEquals(3, expectedMovieList.size());
    }

    @Test
    public void testGetAllSortedByRatingDesc() {
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

        MovieDao movieDao = new JdbcMovieDao(jdbcTemplate);

        RequestSearchParam requestSearchParam = new RequestSearchParam();
        requestSearchParam.setSortType(SortType.DESC);
        requestSearchParam.setSortField(SortField.RATING);

        // When
        when(jdbcTemplate.query(any(String.class), any(MovieRowMapper.class))).thenReturn(expectedMovieList);

        // Then
        List<Movie> actualMovieList = movieDao.getAll(requestSearchParam);

        assertEquals(expectedMovieList.get(0), actualMovieList.get(0));
        assertEquals(expectedMovieList.get(1), actualMovieList.get(1));
        assertEquals(expectedMovieList.get(2), actualMovieList.get(2));

        assertEquals(3, expectedMovieList.size());
    }

    @Test
    public void testGetAllSortedByPriceAsc() {
        JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);

        List<Movie> expectedMovieList = new ArrayList<>();

        Movie movie1 = new Movie();
        movie1.setId(1);
        movie1.setNameRussian("Побег из Шоушенка");
        movie1.setNameNative("The Shawshank Redemption");
        movie1.setYearOfRelease(1994);
        movie1.setRating(8.1);
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
        movie3.setRating(8.6);
        movie3.setPrice(200.60);
        movie3.setPicturePath("path3");
        expectedMovieList.add(movie3);

        MovieDao movieDao = new JdbcMovieDao(jdbcTemplate);

        RequestSearchParam requestSearchParam = new RequestSearchParam();
        requestSearchParam.setSortType(SortType.ASC);
        requestSearchParam.setSortField(SortField.PRICE);

        // When
        when(jdbcTemplate.query(any(String.class), any(MovieRowMapper.class))).thenReturn(expectedMovieList);

        // Then
        List<Movie> actualMovieList = movieDao.getAll(requestSearchParam);

        assertEquals(expectedMovieList.get(0), actualMovieList.get(0));
        assertEquals(expectedMovieList.get(1), actualMovieList.get(1));
        assertEquals(expectedMovieList.get(2), actualMovieList.get(2));

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
        List<Movie> actualMovieList = movieDao.getMoviesByGenre(1, null);

        for (Movie actualMovie : actualMovieList) {
            assertTrue(expectedMovieList.indexOf(actualMovie) > -1);
        }

        assertEquals(3, expectedMovieList.size());
    }

    @Test
    public void testGetMoviesByGenreSortedByPriceAsc() {
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

        RequestSearchParam requestSearchParam = new RequestSearchParam();
        requestSearchParam.setSortType(SortType.ASC);
        requestSearchParam.setSortField(SortField.PRICE);

        // When
        when(jdbcTemplate.query(any(String.class), any(MovieRowMapper.class), any(Integer.class))).thenReturn(expectedMovieList);

        // Then
        List<Movie> actualMovieList = movieDao.getMoviesByGenre(1, requestSearchParam);

        assertEquals(expectedMovieList.get(0), actualMovieList.get(0));
        assertEquals(expectedMovieList.get(1), actualMovieList.get(1));
        assertEquals(expectedMovieList.get(2), actualMovieList.get(2));

        assertEquals(3, expectedMovieList.size());
    }

    @Test
    public void testGetMoviesByGenreSortedByRatingDesc() {
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

        MovieDao movieDao = new JdbcMovieDao(jdbcTemplate);

        RequestSearchParam requestSearchParam = new RequestSearchParam();
        requestSearchParam.setSortType(SortType.DESC);
        requestSearchParam.setSortField(SortField.RATING);

        // When
        when(jdbcTemplate.query(any(String.class), any(MovieRowMapper.class), any(Integer.class))).thenReturn(expectedMovieList);

        // Then
        List<Movie> actualMovieList = movieDao.getMoviesByGenre(1, requestSearchParam);

        assertEquals(expectedMovieList.get(0), actualMovieList.get(0));
        assertEquals(expectedMovieList.get(1), actualMovieList.get(1));
        assertEquals(expectedMovieList.get(2), actualMovieList.get(2));

        assertEquals(3, expectedMovieList.size());
    }

    @Test
    public void testBuildSortedQuery() {
        // Prepare
        String actualSqlRatingDesc = JdbcMovieDao.buildSortedQuery(INITIAL_SQL, SortField.RATING, SortType.DESC);
        String actualSqlPriceDesc = JdbcMovieDao.buildSortedQuery(INITIAL_SQL, SortField.PRICE, SortType.DESC);
        String actualSqlPriceAsc = JdbcMovieDao.buildSortedQuery(INITIAL_SQL, SortField.PRICE, SortType.ASC);

        // Test
        assertEquals(EXPECTED_SQL_RATING_DESC, actualSqlRatingDesc);
        assertEquals(EXPECTED_SQL_PRICE_DESC, actualSqlPriceDesc);
        assertEquals(EXPECTED_SQL_PRICE_ASC, actualSqlPriceAsc);
    }

    @Test
    public void testGetMovieById() throws Exception {
        // Prepare
        JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);

        MovieDetailed expectedMovieDetailed = new MovieDetailed();
        expectedMovieDetailed.setId(1);
        expectedMovieDetailed.setNameRussian("nameRussian1");
        expectedMovieDetailed.setNameNative("nameNative1");
        expectedMovieDetailed.setYearOfRelease(1999);
        expectedMovieDetailed.setDescription("description1");
        expectedMovieDetailed.setRating(8.5);
        expectedMovieDetailed.setPrice(99.99);
        expectedMovieDetailed.setPicturePath("picturePath1");

        // When
        when(jdbcTemplate.queryForObject(any(String.class), any(MovieDetailedRowMapper.class), any(Integer.class))).thenReturn(expectedMovieDetailed);

        // Then
        MovieDao movieDao = new JdbcMovieDao(jdbcTemplate);
        MovieDetailed actualMoviedetailed = movieDao.getMovieById(1);

        assertEquals(expectedMovieDetailed, actualMoviedetailed);
    }

    @Test
    public void testGetUserRatingByMovieId() {
        // Prepare
        JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);

        double expectedRating = 9.9;

        // When
        when(jdbcTemplate.queryForObject(any(String.class), eq(Double.class), any(Integer.class), any(Integer.class))).thenReturn(expectedRating);

        // Then
        MovieDao movieDao = new JdbcMovieDao(jdbcTemplate);
        double actualRating = movieDao.getUserRatingByMovieId(1, 2);

        assertEquals(expectedRating, actualRating, 1e-3);
    }


    @Test
    public void testEntityToIntArray() throws Exception {
        // Prepare
        JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);
        DataSource dataSource = mock(DataSource.class);
        Connection connection = mock(Connection.class);
        Array expectedArray = mock(Array.class);

        Country country1 = new Country(1, "США");

        Country country2 = new Country(3, "Великобритания");

        List<Country> inputCountryList = Arrays.asList(country1, country2);

        // When
        when(jdbcTemplate.getDataSource()).thenReturn(dataSource);
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.createArrayOf(any(String.class), any(Integer[].class))).thenReturn(expectedArray);

        // Then
        JdbcMovieDao movieDao = new JdbcMovieDao(jdbcTemplate);

        Array actualArray = movieDao.entityToIntArray(inputCountryList);

        assertEquals(expectedArray, actualArray);
    }

    @Test
    public void testInsertMovie() {
        // Prepare
        JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);

        int expectedMovieId = 1;

        Movie movie = new Movie();
        movie.setId(1);
        movie.setNameRussian("Побег из Шоушенка");
        movie.setNameNative("The Shawshank Redemption");
        movie.setYearOfRelease(1994);
        movie.setRating(8.9);
        movie.setPrice(123.45);
        movie.setPicturePath("path1");

        // When
        when(jdbcTemplate.queryForObject(any(String.class), eq(Integer.class), any(String.class), any(String.class),
                any(Integer.class), any(String.class), any(Double.class), any(String.class),
                any(Array.class), any(Array.class))).thenReturn(expectedMovieId);

        // Then
        MovieDao movieDao = new JdbcMovieDao(jdbcTemplate);
        int actualMovieId = movieDao.insertMovie(movie);

        assertEquals(expectedMovieId, actualMovieId);
    }

    @Test
    public void testUpdateMovie() {
        // Prepare
        JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);

        int expectedMovieId = 1;

        Movie movie = new Movie();
        movie.setId(1);
        movie.setNameRussian("Побег из Шоушенка");
        movie.setNameNative("The Shawshank Redemption");
        movie.setYearOfRelease(1994);
        movie.setRating(8.9);
        movie.setPrice(123.45);
        movie.setPicturePath("path1");

        // When
        when(jdbcTemplate.queryForObject(any(String.class), eq(Integer.class), any(Integer.class), any(String.class),
                any(String.class), any(String.class), any(Array.class), any(Array.class))).thenReturn(expectedMovieId);

        // Then
        MovieDao movieDao = new JdbcMovieDao(jdbcTemplate);
        int actualMovieId = movieDao.updateMovie(movie);

        assertEquals(expectedMovieId, actualMovieId);
    }
}
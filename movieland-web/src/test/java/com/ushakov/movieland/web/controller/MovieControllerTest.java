package com.ushakov.movieland.web.controller;


import com.ushakov.movieland.common.RequestSearchParam;
import com.ushakov.movieland.common.SortField;
import com.ushakov.movieland.common.SortType;
import com.ushakov.movieland.entity.*;
import com.ushakov.movieland.service.MovieService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/applicationContext.xml", "file:src/main/webapp/WEB-INF/dispatcherServlet-servlet.xml"})
@WebAppConfiguration
public class MovieControllerTest extends AbstractJUnit4SpringContextTests {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MovieController movieController;

    private MovieService movieService = mock(MovieService.class);

    @Before
    public void setUp() {
        movieController.setMovieService(movieService);

        Mockito.reset(movieService);

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGetAll() throws Exception {
        // Prepare
        Movie first = new Movie();
        first.setId(1);
        first.setNameRussian("Побег из Шоушенка");
        first.setNameNative("The Shawshank Redemption");
        first.setYearOfRelease(1994);
        first.setRating(8.9);
        first.setPrice(123.45);
        first.setPicturePath("path1");

        Movie second = new Movie();
        second.setId(2);
        second.setNameRussian("Зеленая миля");
        second.setNameNative("The Green Mile");
        second.setYearOfRelease(1999);
        second.setRating(8.9);
        second.setPrice(134.67);
        second.setPicturePath("path2");

        // When
        when(movieService.getAll(null)).thenReturn(Arrays.asList(first, second));

        // Then
        mockMvc.perform(get("/v1/movie"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", equalTo(1)))
                .andExpect(jsonPath("$[0].nameRussian", equalTo("Побег из Шоушенка")))
                .andExpect(jsonPath("$[0].nameNative", equalTo("The Shawshank Redemption")))
                .andExpect(jsonPath("$[0].yearOfRelease", equalTo(1994)))
                .andExpect(jsonPath("$[0].rating", equalTo(8.9)))
                .andExpect(jsonPath("$[0].price", equalTo(123.45)))
                .andExpect(jsonPath("$[0].picturePath", equalTo("path1")))
                .andExpect(jsonPath("$[1].id", equalTo(2)))
                .andExpect(jsonPath("$[1].nameRussian", equalTo("Зеленая миля")))
                .andExpect(jsonPath("$[1].nameNative", equalTo("The Green Mile")))
                .andExpect(jsonPath("$[1].yearOfRelease", equalTo(1999)))
                .andExpect(jsonPath("$[1].rating", equalTo(8.9)))
                .andExpect(jsonPath("$[1].price", equalTo(134.67)))
                .andExpect(jsonPath("$[1].picturePath", equalTo("path2")));

        verify(movieService, times(1)).getAll(null);
        verifyNoMoreInteractions(movieService);
    }

    @Test
    public void testGetMovieById() throws Exception {
        // Prepare
        Genre genre = new Genre(1, "драма");

        Country country = new Country(1, "США");

        User user = new User(1, "nickname1");

        Review review = new Review(1, user, "some text");


        MovieDetailed expectedMovieDetailed = new MovieDetailed();
        expectedMovieDetailed.setId(1);
        expectedMovieDetailed.setNameRussian("Побег из Шоушенка");
        expectedMovieDetailed.setNameNative("The Shawshank Redemption");
        expectedMovieDetailed.setYearOfRelease(1994);
        expectedMovieDetailed.setRating(8.9);
        expectedMovieDetailed.setPrice(123.45);
        expectedMovieDetailed.setPicturePath("path1");
        expectedMovieDetailed.setGenres(Arrays.asList(genre));
        expectedMovieDetailed.setCountries(Arrays.asList(country));
        expectedMovieDetailed.setReviews(Arrays.asList(review));

        // When
        when(movieService.getMovieById(1, null)).thenReturn(expectedMovieDetailed);

        // Then
        mockMvc.perform(get("/v1/movie/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.nameRussian", equalTo("Побег из Шоушенка")))
                .andExpect(jsonPath("$.nameNative", equalTo("The Shawshank Redemption")))
                .andExpect(jsonPath("$.yearOfRelease", equalTo(1994)))
                .andExpect(jsonPath("$.rating", equalTo(8.9)))
                .andExpect(jsonPath("$.price", equalTo(123.45)))
                .andExpect(jsonPath("$.picturePath", equalTo("path1")))
                .andExpect(jsonPath("$.countries", hasSize(1)))
                .andExpect(jsonPath("$.countries[0].id", equalTo(1)))
                .andExpect(jsonPath("$.countries[0].name", equalTo("США")))
                .andExpect(jsonPath("$.genres", hasSize(1)))
                .andExpect(jsonPath("$.genres[0].id", equalTo(1)))
                .andExpect(jsonPath("$.genres[0].name", equalTo("драма")))
                .andExpect(jsonPath("$.reviews", hasSize(1)))
                .andExpect(jsonPath("$.reviews[0].id", equalTo(1)))
                .andExpect(jsonPath("$.reviews[0].user.id", equalTo(1)))
                .andExpect(jsonPath("$.reviews[0].user.nickname", equalTo("nickname1")))
                .andExpect(jsonPath("$.reviews[0].text", equalTo("some text")));

        verify(movieService, times(1)).getMovieById(1, null);
        verifyNoMoreInteractions(movieService);
    }

    @Test
    public void testGetAllSortedByRatingDesc() throws Exception {
        // Prepare
        Movie first = new Movie();
        first.setId(1);
        first.setNameRussian("Побег из Шоушенка");
        first.setNameNative("The Shawshank Redemption");
        first.setYearOfRelease(1994);
        first.setRating(8.9);
        first.setPrice(123.45);
        first.setPicturePath("path1");

        Movie second = new Movie();
        second.setId(2);
        second.setNameRussian("Зеленая миля");
        second.setNameNative("The Green Mile");
        second.setYearOfRelease(1999);
        second.setRating(9.9);
        second.setPrice(134.67);
        second.setPicturePath("path2");

        RequestSearchParam requestSearchParam = new RequestSearchParam();
        requestSearchParam.setSortType(SortType.DESC);
        requestSearchParam.setSortField(SortField.RATING);

        // When
        when(movieService.getAll(any(RequestSearchParam.class))).thenReturn(Arrays.asList(second, first));

        // Then
        mockMvc.perform(get("/v1/movie?rating=desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[1].id", equalTo(1)))
                .andExpect(jsonPath("$[1].nameRussian", equalTo("Побег из Шоушенка")))
                .andExpect(jsonPath("$[1].nameNative", equalTo("The Shawshank Redemption")))
                .andExpect(jsonPath("$[1].yearOfRelease", equalTo(1994)))
                .andExpect(jsonPath("$[1].rating", equalTo(8.9)))
                .andExpect(jsonPath("$[1].price", equalTo(123.45)))
                .andExpect(jsonPath("$[1].picturePath", equalTo("path1")))
                .andExpect(jsonPath("$[0].id", equalTo(2)))
                .andExpect(jsonPath("$[0].nameRussian", equalTo("Зеленая миля")))
                .andExpect(jsonPath("$[0].nameNative", equalTo("The Green Mile")))
                .andExpect(jsonPath("$[0].yearOfRelease", equalTo(1999)))
                .andExpect(jsonPath("$[0].rating", equalTo(9.9)))
                .andExpect(jsonPath("$[0].price", equalTo(134.67)))
                .andExpect(jsonPath("$[0].picturePath", equalTo("path2")));
    }

    @Test
    public void testGetAllSortedByPriceAsc() throws Exception {
        // Prepare
        Movie first = new Movie();
        first.setId(1);
        first.setNameRussian("Побег из Шоушенка");
        first.setNameNative("The Shawshank Redemption");
        first.setYearOfRelease(1994);
        first.setRating(8.9);
        first.setPrice(223.45);
        first.setPicturePath("path1");

        Movie second = new Movie();
        second.setId(2);
        second.setNameRussian("Зеленая миля");
        second.setNameNative("The Green Mile");
        second.setYearOfRelease(1999);
        second.setRating(9.9);
        second.setPrice(134.67);
        second.setPicturePath("path2");

        RequestSearchParam requestSearchParam = new RequestSearchParam();
        requestSearchParam.setSortType(SortType.ASC);
        requestSearchParam.setSortField(SortField.PRICE);

        // When
        when(movieService.getAll(any(RequestSearchParam.class))).thenReturn(Arrays.asList(second, first));

        // Then
        mockMvc.perform(get("/v1/movie?price=asc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[1].id", equalTo(1)))
                .andExpect(jsonPath("$[1].nameRussian", equalTo("Побег из Шоушенка")))
                .andExpect(jsonPath("$[1].nameNative", equalTo("The Shawshank Redemption")))
                .andExpect(jsonPath("$[1].yearOfRelease", equalTo(1994)))
                .andExpect(jsonPath("$[1].rating", equalTo(8.9)))
                .andExpect(jsonPath("$[1].price", equalTo(223.45)))
                .andExpect(jsonPath("$[1].picturePath", equalTo("path1")))
                .andExpect(jsonPath("$[0].id", equalTo(2)))
                .andExpect(jsonPath("$[0].nameRussian", equalTo("Зеленая миля")))
                .andExpect(jsonPath("$[0].nameNative", equalTo("The Green Mile")))
                .andExpect(jsonPath("$[0].yearOfRelease", equalTo(1999)))
                .andExpect(jsonPath("$[0].rating", equalTo(9.9)))
                .andExpect(jsonPath("$[0].price", equalTo(134.67)))
                .andExpect(jsonPath("$[0].picturePath", equalTo("path2")));
    }

    @Test
    public void testGetThreeRandomMovies() throws Exception {
        // Prepare
        Movie first = new Movie();
        first.setId(1);
        first.setNameRussian("Побег из Шоушенка");
        first.setNameNative("The Shawshank Redemption");
        first.setYearOfRelease(1994);
        first.setRating(8.9);
        first.setPrice(123.45);
        first.setPicturePath("path1");

        Movie second = new Movie();
        second.setId(2);
        second.setNameRussian("Зеленая миля");
        second.setNameNative("The Green Mile");
        second.setYearOfRelease(1999);
        second.setRating(8.9);
        second.setPrice(134.67);
        second.setPicturePath("path2");

        Movie third = new Movie();
        third.setId(3);
        third.setNameRussian("Форрест Гамп");
        third.setNameNative("Forrest Gump");
        third.setYearOfRelease(1994);
        third.setRating(8.6);
        third.setPrice(200.6);
        third.setPicturePath("path3");

        // When
        when(movieService.getThreeRandomMovies()).thenReturn(Arrays.asList(first, second, third));

        // Then
        mockMvc.perform(get("/v1/movie/random"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", notNullValue()))
                .andExpect(jsonPath("$[0].nameRussian", notNullValue()))
                .andExpect(jsonPath("$[0].nameNative", notNullValue()))
                .andExpect(jsonPath("$[0].yearOfRelease", notNullValue()))
                .andExpect(jsonPath("$[0].rating", notNullValue()))
                .andExpect(jsonPath("$[0].price", notNullValue()))
                .andExpect(jsonPath("$[0].picturePath", notNullValue()))
                .andExpect(jsonPath("$[1].id", notNullValue()))
                .andExpect(jsonPath("$[1].nameRussian", notNullValue()))
                .andExpect(jsonPath("$[1].nameNative", notNullValue()))
                .andExpect(jsonPath("$[1].yearOfRelease", notNullValue()))
                .andExpect(jsonPath("$[1].rating", notNullValue()))
                .andExpect(jsonPath("$[1].price", notNullValue()))
                .andExpect(jsonPath("$[1].picturePath", notNullValue()))
                .andExpect(jsonPath("$[2].id", notNullValue()))
                .andExpect(jsonPath("$[2].nameRussian", notNullValue()))
                .andExpect(jsonPath("$[2].nameNative", notNullValue()))
                .andExpect(jsonPath("$[2].yearOfRelease", notNullValue()))
                .andExpect(jsonPath("$[2].rating", notNullValue()))
                .andExpect(jsonPath("$[2].price", notNullValue()))
                .andExpect(jsonPath("$[2].picturePath", notNullValue()));
    }

    @Test
    public void testGetMoviesByGenre() throws Exception {
        // Prepare
        Movie first = new Movie();
        first.setId(1);
        first.setNameRussian("Побег из Шоушенка");
        first.setNameNative("The Shawshank Redemption");
        first.setYearOfRelease(1994);
        first.setRating(8.9);
        first.setPrice(123.45);
        first.setPicturePath("path1");

        Movie second = new Movie();
        second.setId(2);
        second.setNameRussian("Зеленая миля");
        second.setNameNative("The Green Mile");
        second.setYearOfRelease(1999);
        second.setRating(8.9);
        second.setPrice(134.67);
        second.setPicturePath("path2");

        Movie third = new Movie();
        third.setId(3);
        third.setNameRussian("Форрест Гамп");
        third.setNameNative("Forrest Gump");
        third.setYearOfRelease(1994);
        third.setRating(8.6);
        third.setPrice(200.6);
        third.setPicturePath("path3");

        // When
        when(movieService.getMoviesByGenre(1, null)).thenReturn(Arrays.asList(first, second, third));

        // Then
        mockMvc.perform(get("/v1/movie/genre/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", notNullValue()))
                .andExpect(jsonPath("$[0].nameRussian", notNullValue()))
                .andExpect(jsonPath("$[0].nameNative", notNullValue()))
                .andExpect(jsonPath("$[0].yearOfRelease", notNullValue()))
                .andExpect(jsonPath("$[0].rating", notNullValue()))
                .andExpect(jsonPath("$[0].price", notNullValue()))
                .andExpect(jsonPath("$[0].picturePath", notNullValue()))
                .andExpect(jsonPath("$[1].id", notNullValue()))
                .andExpect(jsonPath("$[1].nameRussian", notNullValue()))
                .andExpect(jsonPath("$[1].nameNative", notNullValue()))
                .andExpect(jsonPath("$[1].yearOfRelease", notNullValue()))
                .andExpect(jsonPath("$[1].rating", notNullValue()))
                .andExpect(jsonPath("$[1].price", notNullValue()))
                .andExpect(jsonPath("$[1].picturePath", notNullValue()))
                .andExpect(jsonPath("$[2].id", notNullValue()))
                .andExpect(jsonPath("$[2].nameRussian", notNullValue()))
                .andExpect(jsonPath("$[2].nameNative", notNullValue()))
                .andExpect(jsonPath("$[2].yearOfRelease", notNullValue()))
                .andExpect(jsonPath("$[2].rating", notNullValue()))
                .andExpect(jsonPath("$[2].price", notNullValue()))
                .andExpect(jsonPath("$[2].picturePath", notNullValue()));
    }

    @Test
    public void testGetMoviesByGenreSortedByPriceDesc() throws Exception {
        // Prepare
        Movie first = new Movie();
        first.setId(1);
        first.setNameRussian("Побег из Шоушенка");
        first.setNameNative("The Shawshank Redemption");
        first.setYearOfRelease(1994);
        first.setRating(8.9);
        first.setPrice(323.45);
        first.setPicturePath("path1");

        Movie second = new Movie();
        second.setId(2);
        second.setNameRussian("Зеленая миля");
        second.setNameNative("The Green Mile");
        second.setYearOfRelease(1999);
        second.setRating(8.9);
        second.setPrice(234.67);
        second.setPicturePath("path2");

        Movie third = new Movie();
        third.setId(3);
        third.setNameRussian("Форрест Гамп");
        third.setNameNative("Forrest Gump");
        third.setYearOfRelease(1994);
        third.setRating(8.6);
        third.setPrice(200.6);
        third.setPicturePath("path3");

        RequestSearchParam requestSearchParam = new RequestSearchParam();
        requestSearchParam.setSortType(SortType.DESC);
        requestSearchParam.setSortField(SortField.PRICE);

        // When
        when(movieService.getMoviesByGenre(any(Integer.class), any(RequestSearchParam.class))).thenReturn(Arrays.asList(first, second, third));

        // Then
        mockMvc.perform(get("/v1/movie/genre/1?price=desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", equalTo(1)))
                .andExpect(jsonPath("$[0].nameRussian", equalTo("Побег из Шоушенка")))
                .andExpect(jsonPath("$[0].nameNative", equalTo("The Shawshank Redemption")))
                .andExpect(jsonPath("$[0].yearOfRelease", equalTo(1994)))
                .andExpect(jsonPath("$[0].rating", equalTo(8.9)))
                .andExpect(jsonPath("$[0].price", equalTo(323.45)))
                .andExpect(jsonPath("$[0].picturePath", equalTo("path1")))
                .andExpect(jsonPath("$[1].id", equalTo(2)))
                .andExpect(jsonPath("$[1].nameRussian", equalTo("Зеленая миля")))
                .andExpect(jsonPath("$[1].nameNative", equalTo("The Green Mile")))
                .andExpect(jsonPath("$[1].yearOfRelease", equalTo(1999)))
                .andExpect(jsonPath("$[1].rating", equalTo(8.9)))
                .andExpect(jsonPath("$[1].price", equalTo(234.67)))
                .andExpect(jsonPath("$[1].picturePath", equalTo("path2")))
                .andExpect(jsonPath("$[2].id", equalTo(3)))
                .andExpect(jsonPath("$[2].nameRussian", equalTo("Форрест Гамп")))
                .andExpect(jsonPath("$[2].nameNative", equalTo("Forrest Gump")))
                .andExpect(jsonPath("$[2].yearOfRelease", equalTo(1994)))
                .andExpect(jsonPath("$[2].rating", equalTo(8.6)))
                .andExpect(jsonPath("$[2].price", equalTo(200.6)))
                .andExpect(jsonPath("$[2].picturePath", equalTo("path3")));
    }

    @Test
    public void testGetMoviesByGenreSortedByRatingDesc() throws Exception {
        // Prepare
        Movie first = new Movie();
        first.setId(1);
        first.setNameRussian("Побег из Шоушенка");
        first.setNameNative("The Shawshank Redemption");
        first.setYearOfRelease(1994);
        first.setRating(8.9);
        first.setPrice(323.45);
        first.setPicturePath("path1");

        Movie second = new Movie();
        second.setId(2);
        second.setNameRussian("Зеленая миля");
        second.setNameNative("The Green Mile");
        second.setYearOfRelease(1999);
        second.setRating(8.3);
        second.setPrice(234.67);
        second.setPicturePath("path2");

        Movie third = new Movie();
        third.setId(3);
        third.setNameRussian("Форрест Гамп");
        third.setNameNative("Forrest Gump");
        third.setYearOfRelease(1994);
        third.setRating(8.1);
        third.setPrice(200.6);
        third.setPicturePath("path3");

        RequestSearchParam requestSearchParam = new RequestSearchParam();
        requestSearchParam.setSortType(SortType.DESC);
        requestSearchParam.setSortField(SortField.RATING);

        // When
        when(movieService.getMoviesByGenre(any(Integer.class), any(RequestSearchParam.class))).thenReturn(Arrays.asList(first, second, third));

        // Then
        mockMvc.perform(get("/v1/movie/genre/1?rating=desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", equalTo(1)))
                .andExpect(jsonPath("$[0].nameRussian", equalTo("Побег из Шоушенка")))
                .andExpect(jsonPath("$[0].nameNative", equalTo("The Shawshank Redemption")))
                .andExpect(jsonPath("$[0].yearOfRelease", equalTo(1994)))
                .andExpect(jsonPath("$[0].rating", equalTo(8.9)))
                .andExpect(jsonPath("$[0].price", equalTo(323.45)))
                .andExpect(jsonPath("$[0].picturePath", equalTo("path1")))
                .andExpect(jsonPath("$[1].id", equalTo(2)))
                .andExpect(jsonPath("$[1].nameRussian", equalTo("Зеленая миля")))
                .andExpect(jsonPath("$[1].nameNative", equalTo("The Green Mile")))
                .andExpect(jsonPath("$[1].yearOfRelease", equalTo(1999)))
                .andExpect(jsonPath("$[1].rating", equalTo(8.3)))
                .andExpect(jsonPath("$[1].price", equalTo(234.67)))
                .andExpect(jsonPath("$[1].picturePath", equalTo("path2")))
                .andExpect(jsonPath("$[2].id", equalTo(3)))
                .andExpect(jsonPath("$[2].nameRussian", equalTo("Форрест Гамп")))
                .andExpect(jsonPath("$[2].nameNative", equalTo("Forrest Gump")))
                .andExpect(jsonPath("$[2].yearOfRelease", equalTo(1994)))
                .andExpect(jsonPath("$[2].rating", equalTo(8.1)))
                .andExpect(jsonPath("$[2].price", equalTo(200.6)))
                .andExpect(jsonPath("$[2].picturePath", equalTo("path3")));
    }
}
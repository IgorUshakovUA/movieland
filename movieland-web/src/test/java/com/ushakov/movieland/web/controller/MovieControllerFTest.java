package com.ushakov.movieland.web.controller;

import org.h2.jdbcx.JdbcDataSource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/applicationContext.xml", "file:src/main/webapp/WEB-INF/dispatcherServlet-servlet.xml", "classpath:iTestContext.xml"})
@WebAppConfiguration
public class MovieControllerFTest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private JdbcDataSource jDataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
        jdbcTemplate.setDataSource(jDataSource);

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGetAll() throws Exception {
        mockMvc.perform(get("/v1/movie"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(25)))
                .andExpect(jsonPath("$[0].id", equalTo(1)))
                .andExpect(jsonPath("$[0].nameRussian", equalTo("Побег из Шоушенка")))
                .andExpect(jsonPath("$[0].nameNative", equalTo("The Shawshank Redemption")))
                .andExpect(jsonPath("$[0].yearOfRelease", equalTo(1994)))
                .andExpect(jsonPath("$[0].rating", equalTo(8.9)))
                .andExpect(jsonPath("$[0].price", equalTo(123.45)))
                .andExpect(jsonPath("$[0].picturePath", equalTo("https://images-na.ssl-images-amazon.com/images/M/MV5BODU4MjU4NjIwNl5BMl5BanBnXkFtZTgwMDU2MjEyMDE@._V1._SY209_CR0,0,140,209_.jpg")))
                .andExpect(jsonPath("$[1].id", equalTo(2)))
                .andExpect(jsonPath("$[1].nameRussian", equalTo("Зеленая миля")))
                .andExpect(jsonPath("$[1].nameNative", equalTo("The Green Mile")))
                .andExpect(jsonPath("$[1].yearOfRelease", equalTo(1999)))
                .andExpect(jsonPath("$[1].rating", equalTo(8.9)))
                .andExpect(jsonPath("$[1].price", equalTo(134.67)))
                .andExpect(jsonPath("$[1].picturePath", equalTo("https://images-na.ssl-images-amazon.com/images/M/MV5BMTUxMzQyNjA5MF5BMl5BanBnXkFtZTYwOTU2NTY3._V1._SY209_CR0,0,140,209_.jpg")));
    }

    @Test
    public void testGetAllSortedByRatingDesc() throws Exception {
        mockMvc.perform(get("/v1/movie?rating=desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(25)))
                .andExpect(jsonPath("$[0].id", equalTo(1)))
                .andExpect(jsonPath("$[0].nameRussian", equalTo("Побег из Шоушенка")))
                .andExpect(jsonPath("$[0].nameNative", equalTo("The Shawshank Redemption")))
                .andExpect(jsonPath("$[0].yearOfRelease", equalTo(1994)))
                .andExpect(jsonPath("$[0].rating", equalTo(8.9)))
                .andExpect(jsonPath("$[0].price", equalTo(123.45)))
                .andExpect(jsonPath("$[0].picturePath", equalTo("https://images-na.ssl-images-amazon.com/images/M/MV5BODU4MjU4NjIwNl5BMl5BanBnXkFtZTgwMDU2MjEyMDE@._V1._SY209_CR0,0,140,209_.jpg")));
    }

    @Test
    public void testGetAllSortedByPriceDesc() throws Exception {
        mockMvc.perform(get("/v1/movie?price=desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(25)))
                .andExpect(jsonPath("$[0].id", equalTo(3)))
                .andExpect(jsonPath("$[0].nameRussian", equalTo("Форрест Гамп")))
                .andExpect(jsonPath("$[0].nameNative", equalTo("Forrest Gump")))
                .andExpect(jsonPath("$[0].yearOfRelease", equalTo(1994)))
                .andExpect(jsonPath("$[0].rating", equalTo(8.6)))
                .andExpect(jsonPath("$[0].price", equalTo(200.6)))
                .andExpect(jsonPath("$[0].picturePath", equalTo("https://images-na.ssl-images-amazon.com/images/M/MV5BNWIwODRlZTUtY2U3ZS00Yzg1LWJhNzYtMmZiYmEyNmU1NjMzXkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1._SY209_CR2,0,140,209_.jpg")));
    }

    @Test
    public void testGetThreeRandomMovies() throws Exception {
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
    public void testGetMoviesByGenreSortedByRatingDesc() throws Exception {
        mockMvc.perform(get("/v1/movie/genre/1?rating=desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(16)))
                .andExpect(jsonPath("$[0].id", equalTo(1)))
                .andExpect(jsonPath("$[0].nameRussian", equalTo("Побег из Шоушенка")))
                .andExpect(jsonPath("$[0].nameNative", equalTo("The Shawshank Redemption")))
                .andExpect(jsonPath("$[0].yearOfRelease", equalTo(1994)))
                .andExpect(jsonPath("$[0].rating", equalTo(8.9)))
                .andExpect(jsonPath("$[0].price", equalTo(123.45)))
                .andExpect(jsonPath("$[0].picturePath", equalTo("https://images-na.ssl-images-amazon.com/images/M/MV5BODU4MjU4NjIwNl5BMl5BanBnXkFtZTgwMDU2MjEyMDE@._V1._SY209_CR0,0,140,209_.jpg")));
    }

    @Test
    public void testGetMoviesByGenreSortedByRatingAscBadRequest() throws Exception {
        mockMvc.perform(get("/v1/movie/genre/1?rating=asc"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testGetMoviesByGenreSortedByPriceDesc() throws Exception {
        mockMvc.perform(get("/v1/movie/genre/1?price=desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(16)))
                .andExpect(jsonPath("$[0].id", equalTo(3)))
                .andExpect(jsonPath("$[0].nameRussian", equalTo("Форрест Гамп")))
                .andExpect(jsonPath("$[0].nameNative", equalTo("Forrest Gump")))
                .andExpect(jsonPath("$[0].yearOfRelease", equalTo(1994)))
                .andExpect(jsonPath("$[0].rating", equalTo(8.6)))
                .andExpect(jsonPath("$[0].price", equalTo(200.6)))
                .andExpect(jsonPath("$[0].picturePath", equalTo("https://images-na.ssl-images-amazon.com/images/M/MV5BNWIwODRlZTUtY2U3ZS00Yzg1LWJhNzYtMmZiYmEyNmU1NjMzXkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1._SY209_CR2,0,140,209_.jpg")));
    }

    @Test
    public void testGetMoviesByGenre() throws Exception {
        mockMvc.perform(get("/v1/movie/genre/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(16)))
                .andExpect(jsonPath("$[0].id", notNullValue()))
                .andExpect(jsonPath("$[0].nameRussian", notNullValue()))
                .andExpect(jsonPath("$[0].nameNative", notNullValue()))
                .andExpect(jsonPath("$[0].yearOfRelease", notNullValue()))
                .andExpect(jsonPath("$[0].rating", notNullValue()))
                .andExpect(jsonPath("$[0].price", notNullValue()))
                .andExpect(jsonPath("$[0].picturePath", notNullValue()));
    }
}

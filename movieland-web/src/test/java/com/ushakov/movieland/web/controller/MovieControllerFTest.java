package com.ushakov.movieland.web.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.*;
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

    @Before
    public void setUp() {
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
    public void testGetMovieById() throws Exception {
        mockMvc.perform(get("/v1/movie/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.nameRussian", equalTo("Побег из Шоушенка")))
                .andExpect(jsonPath("$.nameNative", equalTo("The Shawshank Redemption")))
                .andExpect(jsonPath("$.yearOfRelease", equalTo(1994)))
                .andExpect(jsonPath("$.rating", equalTo(8.9)))
                .andExpect(jsonPath("$.price", equalTo(123.45)))
                .andExpect(jsonPath("$.picturePath", equalTo("https://images-na.ssl-images-amazon.com/images/M/MV5BODU4MjU4NjIwNl5BMl5BanBnXkFtZTgwMDU2MjEyMDE@._V1._SY209_CR0,0,140,209_.jpg")))
                .andExpect(jsonPath("$.countries", hasSize(1)))
                .andExpect(jsonPath("$.countries[0].id", equalTo(1)))
                .andExpect(jsonPath("$.countries[0].name", equalTo("США")))
                .andExpect(jsonPath("$.genres", hasSize(2)))
                .andExpect(jsonPath("$.genres[0].id", equalTo(1)))
                .andExpect(jsonPath("$.genres[0].name", equalTo("драма")))
                .andExpect(jsonPath("$.genres[1].id", equalTo(2)))
                .andExpect(jsonPath("$.genres[1].name", equalTo("криминал")))
                .andExpect(jsonPath("$.reviews", hasSize(2)))
                .andExpect(jsonPath("$.reviews[0].id", equalTo(1)))
                .andExpect(jsonPath("$.reviews[0].user.id", equalTo(2)))
                .andExpect(jsonPath("$.reviews[0].user.nickname", equalTo("bricks")))
                .andExpect(jsonPath("$.reviews[0].text", equalTo("Гениальное кино! Смотришь и думаешь «Так не бывает!», но позже понимаешь, что только так и должно быть. Начинаешь заново осмысливать значение фразы, которую постоянно используешь в своей жизни, «Надежда умирает последней». Ведь если ты не надеешься, то все в твоей жизни гаснет, не остается смысла. Фильм наполнен бесконечным числом правильных афоризмов. Я уверена, что буду пересматривать его сотни раз.")))
                .andExpect(jsonPath("$.reviews[1].id", equalTo(2)))
                .andExpect(jsonPath("$.reviews[1].user.id", equalTo(3)))
                .andExpect(jsonPath("$.reviews[1].user.nickname", equalTo("hjkl")))
                .andExpect(jsonPath("$.reviews[1].text", equalTo("Кино это является, безусловно, «со знаком качества». Что же до первого места в рейтинге, то, думаю, здесь имело место быть выставление «десяточек» от большинства зрителей вкупе с раздутыми восторженными откликами кинокритиков. Фильм атмосферный. Он драматичный. И, конечно, заслуживает того, чтобы находиться довольно высоко в мировом кинематографе.")));
    }

    @Test
    public void testGetMovieByIdEur() throws Exception {
        mockMvc.perform(get("/v1/movie/1?currency=EUR"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.nameRussian", equalTo("Побег из Шоушенка")))
                .andExpect(jsonPath("$.nameNative", equalTo("The Shawshank Redemption")))
                .andExpect(jsonPath("$.yearOfRelease", equalTo(1994)))
                .andExpect(jsonPath("$.rating", equalTo(8.9)))
                .andExpect(jsonPath("$.price", lessThan(123.45)))
                .andExpect(jsonPath("$.picturePath", equalTo("https://images-na.ssl-images-amazon.com/images/M/MV5BODU4MjU4NjIwNl5BMl5BanBnXkFtZTgwMDU2MjEyMDE@._V1._SY209_CR0,0,140,209_.jpg")))
                .andExpect(jsonPath("$.countries", hasSize(1)))
                .andExpect(jsonPath("$.countries[0].id", equalTo(1)))
                .andExpect(jsonPath("$.countries[0].name", equalTo("США")))
                .andExpect(jsonPath("$.genres", hasSize(2)))
                .andExpect(jsonPath("$.genres[0].id", equalTo(1)))
                .andExpect(jsonPath("$.genres[0].name", equalTo("драма")))
                .andExpect(jsonPath("$.genres[1].id", equalTo(2)))
                .andExpect(jsonPath("$.genres[1].name", equalTo("криминал")))
                .andExpect(jsonPath("$.reviews", hasSize(2)))
                .andExpect(jsonPath("$.reviews[0].id", equalTo(1)))
                .andExpect(jsonPath("$.reviews[0].user.id", equalTo(2)))
                .andExpect(jsonPath("$.reviews[0].user.nickname", equalTo("bricks")))
                .andExpect(jsonPath("$.reviews[0].text", equalTo("Гениальное кино! Смотришь и думаешь «Так не бывает!», но позже понимаешь, что только так и должно быть. Начинаешь заново осмысливать значение фразы, которую постоянно используешь в своей жизни, «Надежда умирает последней». Ведь если ты не надеешься, то все в твоей жизни гаснет, не остается смысла. Фильм наполнен бесконечным числом правильных афоризмов. Я уверена, что буду пересматривать его сотни раз.")))
                .andExpect(jsonPath("$.reviews[1].id", equalTo(2)))
                .andExpect(jsonPath("$.reviews[1].user.id", equalTo(3)))
                .andExpect(jsonPath("$.reviews[1].user.nickname", equalTo("hjkl")))
                .andExpect(jsonPath("$.reviews[1].text", equalTo("Кино это является, безусловно, «со знаком качества». Что же до первого места в рейтинге, то, думаю, здесь имело место быть выставление «десяточек» от большинства зрителей вкупе с раздутыми восторженными откликами кинокритиков. Фильм атмосферный. Он драматичный. И, конечно, заслуживает того, чтобы находиться довольно высоко в мировом кинематографе.")));
    }

    @Test
    public void testGetMovieByIdUsd() throws Exception {
        mockMvc.perform(get("/v1/movie/1?currency=USD"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.nameRussian", equalTo("Побег из Шоушенка")))
                .andExpect(jsonPath("$.nameNative", equalTo("The Shawshank Redemption")))
                .andExpect(jsonPath("$.yearOfRelease", equalTo(1994)))
                .andExpect(jsonPath("$.rating", equalTo(8.9)))
                .andExpect(jsonPath("$.price", lessThan(123.45)))
                .andExpect(jsonPath("$.picturePath", equalTo("https://images-na.ssl-images-amazon.com/images/M/MV5BODU4MjU4NjIwNl5BMl5BanBnXkFtZTgwMDU2MjEyMDE@._V1._SY209_CR0,0,140,209_.jpg")))
                .andExpect(jsonPath("$.countries", hasSize(1)))
                .andExpect(jsonPath("$.countries[0].id", equalTo(1)))
                .andExpect(jsonPath("$.countries[0].name", equalTo("США")))
                .andExpect(jsonPath("$.genres", hasSize(2)))
                .andExpect(jsonPath("$.genres[0].id", equalTo(1)))
                .andExpect(jsonPath("$.genres[0].name", equalTo("драма")))
                .andExpect(jsonPath("$.genres[1].id", equalTo(2)))
                .andExpect(jsonPath("$.genres[1].name", equalTo("криминал")))
                .andExpect(jsonPath("$.reviews", hasSize(2)))
                .andExpect(jsonPath("$.reviews[0].id", equalTo(1)))
                .andExpect(jsonPath("$.reviews[0].user.id", equalTo(2)))
                .andExpect(jsonPath("$.reviews[0].user.nickname", equalTo("bricks")))
                .andExpect(jsonPath("$.reviews[0].text", equalTo("Гениальное кино! Смотришь и думаешь «Так не бывает!», но позже понимаешь, что только так и должно быть. Начинаешь заново осмысливать значение фразы, которую постоянно используешь в своей жизни, «Надежда умирает последней». Ведь если ты не надеешься, то все в твоей жизни гаснет, не остается смысла. Фильм наполнен бесконечным числом правильных афоризмов. Я уверена, что буду пересматривать его сотни раз.")))
                .andExpect(jsonPath("$.reviews[1].id", equalTo(2)))
                .andExpect(jsonPath("$.reviews[1].user.id", equalTo(3)))
                .andExpect(jsonPath("$.reviews[1].user.nickname", equalTo("hjkl")))
                .andExpect(jsonPath("$.reviews[1].text", equalTo("Кино это является, безусловно, «со знаком качества». Что же до первого места в рейтинге, то, думаю, здесь имело место быть выставление «десяточек» от большинства зрителей вкупе с раздутыми восторженными откликами кинокритиков. Фильм атмосферный. Он драматичный. И, конечно, заслуживает того, чтобы находиться довольно высоко в мировом кинематографе.")));
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

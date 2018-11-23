package com.ushakov.movieland.web.controller;

import com.ushakov.movieland.entity.Genre;
import com.ushakov.movieland.service.GenreService;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/applicationContext.xml", "file:src/main/webapp/WEB-INF/dispatcherServlet-servlet.xml"})
@WebAppConfiguration
public class GenreControllerTest extends AbstractJUnit4SpringContextTests {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private GenreController genreController;

    private GenreService genreService = mock(GenreService.class);

    @Before
    public void before() {
        genreController.setGenreService(genreService);

        Mockito.reset(genreService);

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGetAll() throws Exception {
        // Prepare
        Genre first = new Genre(1, "драма");

        Genre second = new Genre(2, "криминал");

        // When
        when(genreService.getAll()).thenReturn(Arrays.asList(first, second));

        // Then
        mockMvc.perform(get("/v1/genre"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", equalTo(1)))
                .andExpect(jsonPath("$[0].name", equalTo("драма")))
                .andExpect(jsonPath("$[1].id", equalTo(2)))
                .andExpect(jsonPath("$[1].name", equalTo("криминал")));

        verify(genreService, times(1)).getAll();
        verifyNoMoreInteractions(genreService);
    }
}
package com.ushakov.movieland.web.controller;

import com.ushakov.movieland.entity.Country;
import com.ushakov.movieland.entity.Genre;
import com.ushakov.movieland.service.CountryService;
import com.ushakov.movieland.service.GenreService;
import com.ushakov.movieland.web.configuration.TestConfiguration;
import com.ushakov.movieland.web.configuration.WebConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfiguration.class, TestConfiguration.class})
@WebAppConfiguration
public class CountryControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private CountryController countryController;

    private CountryService countryService = mock(CountryService.class);

    @Before
    public void before() {
        countryController.setCountryService(countryService);

        Mockito.reset(countryService);

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGetAll() throws Exception {
        // Prepare
        Country first = new Country(1, "США");

        Country second = new Country(2, "Великобритания");

        // When
        when(countryService.getAll()).thenReturn(Arrays.asList(first, second));

        // Then
        mockMvc.perform(get("/v1/country"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", equalTo(1)))
                .andExpect(jsonPath("$[0].name", equalTo("США")))
                .andExpect(jsonPath("$[1].id", equalTo(2)))
                .andExpect(jsonPath("$[1].name", equalTo("Великобритания")));

        verify(countryService, times(1)).getAll();
        verifyNoMoreInteractions(countryService);
    }

}
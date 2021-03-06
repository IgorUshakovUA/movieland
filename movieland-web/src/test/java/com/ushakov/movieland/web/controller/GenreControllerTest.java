package com.ushakov.movieland.web.controller;

import com.ushakov.movieland.common.Credentials;
import com.ushakov.movieland.common.SecurityToken;
import com.ushakov.movieland.common.UserRole;
import com.ushakov.movieland.dao.SecurityDao;
import com.ushakov.movieland.entity.Genre;
import com.ushakov.movieland.service.DefaultSecurityService;
import com.ushakov.movieland.service.GenreService;
import com.ushakov.movieland.web.configuration.DispatcherContextConfiguration;
import com.ushakov.movieland.web.configuration.InterceptorConfig;
import com.ushakov.movieland.web.configuration.TestConfiguration;
import com.ushakov.movieland.web.configuration.AppContextConfiguration;
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
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.UUID;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppContextConfiguration.class, DispatcherContextConfiguration.class, InterceptorConfig.class, TestConfiguration.class})
@WebAppConfiguration
public class GenreControllerTest extends AbstractJUnit4SpringContextTests {
    private static final String USER_UUID = UUID.randomUUID().toString();

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private GenreController genreController;

    private GenreService genreService = mock(GenreService.class);

    @Autowired
    private DefaultSecurityService securityService;

    private SecurityDao securityDao = mock(SecurityDao.class);

    @Before
    public void before() {
        genreController.setGenreService(genreService);

        securityService.setSecurityDao(securityDao);

        SecurityToken securityToken = new SecurityToken();
        securityToken.setUuid(USER_UUID);
        securityToken.setNickName("nickName");
        securityToken.setUserRole(UserRole.USER);
        securityToken.setId(1);

        when(securityDao.logon(any(Credentials.class))).thenReturn(securityToken);

        securityService.logon(new Credentials("my@email.com","password"));

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
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/v1/genre")
                .header("uuid", USER_UUID);

        mockMvc.perform(builder)
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
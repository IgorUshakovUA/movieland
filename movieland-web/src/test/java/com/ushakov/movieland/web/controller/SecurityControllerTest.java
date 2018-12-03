package com.ushakov.movieland.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ushakov.movieland.common.Credentials;
import com.ushakov.movieland.common.SecurityToken;
import com.ushakov.movieland.service.SecurityService;
import com.ushakov.movieland.web.configuration.AppContextConfiguration;
import com.ushakov.movieland.web.configuration.DispatcherContextConfiguration;
import com.ushakov.movieland.web.configuration.TestConfiguration;
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
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppContextConfiguration.class, DispatcherContextConfiguration.class, TestConfiguration.class})
@WebAppConfiguration
public class SecurityControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private SecurityController securityControllerr;

    private SecurityService securityService = mock(SecurityService.class);

    @Before
    public void before() {
        securityControllerr.setSecurityService(securityService);

        Mockito.reset(securityService);

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testLogon() throws Exception {
        // Prepare
        SecurityToken expectedSecurityTocken = new SecurityToken(UUID.randomUUID().toString(), "nickname");

        Credentials credentials = new Credentials("my@email.com", "password");

        // When
        when(securityService.logon(any(Credentials.class))).thenReturn(expectedSecurityTocken);

        // Then
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put("/v1/login")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(credentials));

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.uuid", equalTo(expectedSecurityTocken.getUuid())))
                .andExpect(jsonPath("$.nickName", equalTo(expectedSecurityTocken.getNickName())));
    }

    @Test
    public void testLogonFails() throws Exception {
        // Prepare
        Credentials credentials = new Credentials("my@email.com", "password");

        // When
        when(securityService.logon(any(Credentials.class))).thenReturn(null);

        // Then
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put("/v1/login")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(credentials));

        mockMvc.perform(builder)
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testLogout() throws  Exception {
        // Prepare
        SecurityToken expectedSecurityTocken = new SecurityToken(UUID.randomUUID().toString(), "nickname");

        // When
        when(securityService.logout(any(String.class))).thenReturn(expectedSecurityTocken);

        // Then
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.delete("/v1/logout").param("uuid", expectedSecurityTocken.getUuid());

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.uuid", equalTo(expectedSecurityTocken.getUuid())))
                .andExpect(jsonPath("$.nickName", equalTo(expectedSecurityTocken.getNickName())));
    }

    @Test
    public void testLogoutFails() throws  Exception {
        // When
        when(securityService.logout(any(String.class))).thenReturn(null);

        // Then
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.delete("/v1/logout").param("uuid", "wrong value");

        mockMvc.perform(builder)
                .andExpect(status().is4xxClientError());
    }
}
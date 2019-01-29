package com.ushakov.movieland.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ushakov.movieland.common.Credentials;
import com.ushakov.movieland.common.SecurityToken;
import com.ushakov.movieland.common.UserRole;
import com.ushakov.movieland.dao.SecurityDao;
import com.ushakov.movieland.common.ReviewRequest;
import com.ushakov.movieland.entity.Review;
import com.ushakov.movieland.entity.User;
import com.ushakov.movieland.service.DefaultSecurityService;
import com.ushakov.movieland.service.ReviewService;
import com.ushakov.movieland.web.configuration.AppContextConfiguration;
import com.ushakov.movieland.web.configuration.DispatcherContextConfiguration;
import com.ushakov.movieland.web.configuration.InterceptorConfig;
import com.ushakov.movieland.web.configuration.TestConfiguration;
import com.ushakov.movieland.web.interceptor.CheckAccessRequestInterceptor;
import com.ushakov.movieland.web.interceptor.LogRequestInterceptor;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppContextConfiguration.class, DispatcherContextConfiguration.class, InterceptorConfig.class, TestConfiguration.class})
@WebAppConfiguration
public class ReviewControllerTest {
    private static final String USER_UUID = UUID.randomUUID().toString();

    private final Credentials credentials = new Credentials("my@email.com","password");

    private final SecurityToken securityToken;

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ReviewController reviewController;

    private ReviewService reviewService = mock(ReviewService.class);

    @Autowired
    private DefaultSecurityService securityService;

    private SecurityDao securityDao = mock(SecurityDao.class);

    @Autowired
    private LogRequestInterceptor logRequestInterceptor;

    @Autowired
    private CheckAccessRequestInterceptor checkAccessRequestInterceptor;

    public ReviewControllerTest() {
        super();

        securityToken = new SecurityToken();
        securityToken.setUuid(USER_UUID);
        securityToken.setNickName("nickName");
        securityToken.setUserRole(UserRole.USER);
        securityToken.setId(1);
    }

    @Before
    public void before() {
        Mockito.reset(reviewService);

        Mockito.reset(securityDao);

        reviewController.setReviewService(reviewService);

        securityService.setSecurityDao(securityDao);

        logRequestInterceptor.setSecurityService(securityService);

        checkAccessRequestInterceptor.setSecurityService(securityService);

        when(securityDao.logon(credentials)).thenReturn(securityToken);

        securityService.logon(credentials);

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testAddReview() throws Exception {
        // Prepare
        User user = new User(1,"nickName");
        Review expectedReview = new Review(1,user,"the text of the review");
        ReviewRequest reviewRequest = new ReviewRequest(1, expectedReview.getText());

        // When
        when(reviewService.addReview(any(ReviewRequest.class))).thenReturn(expectedReview);


        // Then
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/v1/review")
                .header("uuid", USER_UUID)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(reviewRequest));

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", equalTo(expectedReview.getId())))
                .andExpect(jsonPath("$.user.id", equalTo(user.getId())))
                .andExpect(jsonPath("$.user.nickname", equalTo(user.getNickname())))
                .andExpect(jsonPath("$.text", equalTo(expectedReview.getText())));
    }

    @Test
    public void testAddReviewFailed() throws Exception {
        // Prepare
        User user = new User(1,"nickName");
        Review expectedReview = new Review(1,user,"the text of the review");
        ReviewRequest reviewRequest = new ReviewRequest(1, expectedReview.getText());

        // When
        when(reviewService.addReview(any(ReviewRequest.class))).thenReturn(null);


        // Then
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/v1/review")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(reviewRequest))
                .header("uuid", USER_UUID);

        mockMvc.perform(builder)
                .andExpect(status().is5xxServerError());
    }

}
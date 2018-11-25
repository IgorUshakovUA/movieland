package com.ushakov.movieland.dao.jdbc;

import com.ushakov.movieland.dao.ReviewDao;
import com.ushakov.movieland.dao.jdbc.mapper.ReviewRowMapper;
import com.ushakov.movieland.entity.Review;
import com.ushakov.movieland.entity.User;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JdbcReviewDaoTest {

    @Test
    public void testGetReviewesByMovieId() {
        // Prepare
        JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);
        ReviewRowMapper reviewRowMapper = mock(ReviewRowMapper.class);

        User user1 = new User(1, "nickname1");

        User user2 = new User(2, "nickname2");

        Review review1 = new Review(1, user1, "some text 1");

        Review review2 = new Review(2, user2, "some text 2");

        List<Review> expectedReviewList = Arrays.asList(review1, review2);

        // When
        when(jdbcTemplate.query(any(String.class), any(ReviewRowMapper.class), any(Integer.class))).thenReturn(Arrays.asList(review1, review2));

        // Then
        ReviewDao reviewDao = new JdbcReviewDao(jdbcTemplate, reviewRowMapper);
        List<Review> actualReviewList = reviewDao.getReviewesByMovieId(1);

        assertEquals(expectedReviewList, actualReviewList);
    }

}
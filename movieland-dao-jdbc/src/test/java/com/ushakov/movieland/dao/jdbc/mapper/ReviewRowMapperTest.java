package com.ushakov.movieland.dao.jdbc.mapper;

import com.ushakov.movieland.dao.UserDao;
import com.ushakov.movieland.entity.Review;
import com.ushakov.movieland.entity.User;
import org.junit.Test;

import java.sql.ResultSet;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ReviewRowMapperTest {
    @Test
    public void testMapRow() throws Exception {
        // Prepare
        UserDao userDao = mock(UserDao.class);
        ResultSet resultSet = mock(ResultSet.class);

        User user = new User(1, "my nick name");

        Review expectedReview = new Review(1, user, "some text");

        // When
        when(userDao.getUserById(1)).thenReturn(user);
        when(resultSet.getInt("userId")).thenReturn(1);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("comment")).thenReturn("some text");

        // Then
        ReviewRowMapper rowMapper = new ReviewRowMapper(userDao);
        Review actualreview = rowMapper.mapRow(resultSet, 1);

        assertEquals(expectedReview, actualreview);
    }

}
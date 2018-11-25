package com.ushakov.movieland.dao.jdbc.mapper;

import com.ushakov.movieland.dao.UserDao;
import com.ushakov.movieland.entity.Review;
import com.ushakov.movieland.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ReviewRowMapper implements RowMapper<Review> {
    private UserDao userDao;

    @Autowired
    public ReviewRowMapper(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public Review mapRow(ResultSet resultSet, int i) throws SQLException {
        User user = userDao.getUserById(resultSet.getInt("userId"));
        Review review = new Review(resultSet.getInt("id"), user, resultSet.getString("comment"));

        return review;
    }
}

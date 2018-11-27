package com.ushakov.movieland.dao.jdbc.mapper;

import com.ushakov.movieland.entity.Review;
import com.ushakov.movieland.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReviewRowMapper implements RowMapper<Review> {
    @Override
    public Review mapRow(ResultSet resultSet, int i) throws SQLException {
        User user = new User(resultSet.getInt("userId"), resultSet.getString("nickName"));

        Review review = new Review(resultSet.getInt("id"), user, resultSet.getString("comment"));

        return review;
    }
}

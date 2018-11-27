package com.ushakov.movieland.dao.jdbc.mapper;

import com.ushakov.movieland.entity.MovieDetailed;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MovieDetailedRowMapper implements RowMapper<MovieDetailed> {
    @Override
    public MovieDetailed mapRow(ResultSet resultSet, int i) throws SQLException {
        MovieDetailed movieDetailed = new MovieDetailed();
        movieDetailed.setId(resultSet.getInt("id"));
        movieDetailed.setNameRussian(resultSet.getString("nameRussian"));
        movieDetailed.setNameNative(resultSet.getString("nameNative"));
        movieDetailed.setYearOfRelease(resultSet.getInt("yearOfRelease"));
        movieDetailed.setDescritpion(resultSet.getString("description"));
        movieDetailed.setRating(resultSet.getDouble("rating"));
        movieDetailed.setPrice(resultSet.getDouble("price"));
        movieDetailed.setPicturePath(resultSet.getString("picturePath"));

        return movieDetailed;
    }
}

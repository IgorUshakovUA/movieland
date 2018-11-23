package com.ushakov.movieland.dao.jdbc.mapper;

import com.ushakov.movieland.entity.Genre;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GenreRowMapper implements RowMapper<Genre> {
    @Override
    public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
        Genre genre = new Genre(resultSet.getInt("id"), resultSet.getString("name"));
        return genre;
    }
}

package com.ushakov.movieland.dao.jdbc.mapper;

import com.ushakov.movieland.entity.Country;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryRowMapper implements RowMapper<Country> {
    @Override
    public Country mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Country(resultSet.getInt("id"), resultSet.getString("name"));
    }
}

package com.ushakov.movieland.dao.jdbc;

import com.ushakov.movieland.dao.CountryDao;
import com.ushakov.movieland.dao.jdbc.mapper.CountryRowMapper;
import com.ushakov.movieland.entity.Country;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JdbcCountryDaoTest {

    @Test
    public void testGetCountriesByCountryGroupId() {
        // Prapare
        JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);

        Country country1 = new Country(1, "США");

        Country country2 = new Country(3, "Великобритания");

        List<Country> expectedCountryList = Arrays.asList(country1, country2);

        // When
        when(jdbcTemplate.query(any(String.class), any(CountryRowMapper.class), any(Integer.class))).thenReturn(Arrays.asList(country1, country2));

        // Then
        CountryDao countryDao = new JdbcCountryDao(jdbcTemplate);
        List<Country> actualCountryList = countryDao.getCountriesByMovieId(3);

        assertEquals(expectedCountryList, actualCountryList);
    }

    @Test
    public void testGetAll() {
        // Prepare
        JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);

        Country country1 = new Country(1, "США");

        Country country2 = new Country(2, "Великобритания");

        List<Country> expectedCountryList = Arrays.asList(country1, country2);

        // When
        when(jdbcTemplate.query(any(String.class), any(CountryRowMapper.class))).thenReturn(Arrays.asList(country1, country2));

        // Then
        CountryDao countryDao = new JdbcCountryDao(jdbcTemplate);
        List<Country> actualCountryList = countryDao.getAll();

        assertEquals(expectedCountryList, actualCountryList);
    }
}
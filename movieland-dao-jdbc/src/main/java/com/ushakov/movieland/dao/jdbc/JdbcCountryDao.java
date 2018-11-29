package com.ushakov.movieland.dao.jdbc;

import com.ushakov.movieland.dao.CountryDao;
import com.ushakov.movieland.dao.jdbc.mapper.CountryRowMapper;
import com.ushakov.movieland.entity.Country;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcCountryDao implements CountryDao {
    private static final String GET_COUNTRIES_BY_COUNTRY_GROUP_ID_SQL = "SELECT country.id, country.name FROM movie, country, countryGroup WHERE movie.id = ? AND movie.countryGroupid = countryGroup.id AND countryGroup.countryId = country.id";
    private static final String GET_ALL_COUNTRIES_SQL = "SELECT id, name FROM country";
    private static final CountryRowMapper COUNTRY_ROW_MAPPER = new CountryRowMapper();
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcCountryDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Country> getCountriesByMovieId(int movieId) {
        List<Country> countryList = jdbcTemplate.query(GET_COUNTRIES_BY_COUNTRY_GROUP_ID_SQL, COUNTRY_ROW_MAPPER, movieId);

        logger.debug("Countries by movieId = {}, size: {}", movieId, countryList.size());
        logger.trace("Countries: {}", countryList);

        return countryList;
    }

    @Override
    public List<Country> getAll() {
        List<Country> countryList = jdbcTemplate.query(GET_ALL_COUNTRIES_SQL, COUNTRY_ROW_MAPPER);

        logger.debug("Countries, size: {}", countryList.size());
        logger.trace("Countries: {}", countryList);

        return countryList;
    }
}

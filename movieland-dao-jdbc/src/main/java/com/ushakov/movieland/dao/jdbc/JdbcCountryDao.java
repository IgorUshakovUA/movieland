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
    private static final String GET_COUNTRIES_BY_COUNTRY_GROUP_ID_SQL = "SELECT country.id, country.name FROM country, countryGroup WHERE countryGroup.id = ? AND countryGroup.countryId = country.id";
    private static final CountryRowMapper COUNTRY_ROW_MAPPER = new CountryRowMapper();
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcCountryDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Country> getCountriesByCountryGroupId(int countryGroupId) {
        List<Country> countryList = jdbcTemplate.query(GET_COUNTRIES_BY_COUNTRY_GROUP_ID_SQL, COUNTRY_ROW_MAPPER, countryGroupId);

        logger.debug("Countries by countryGroupId = {}, size: {}", countryGroupId, countryList.size());
        logger.trace("Countries: {}", countryList);

        return countryList;
    }
}

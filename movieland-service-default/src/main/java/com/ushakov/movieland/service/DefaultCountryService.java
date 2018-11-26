package com.ushakov.movieland.service;

import com.ushakov.movieland.dao.CountryDao;
import com.ushakov.movieland.entity.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultCountryService implements CountryService {
    private CountryDao countryDao;

    @Autowired
    public DefaultCountryService(CountryDao countryDao) {
        this.countryDao = countryDao;
    }

    @Override
    public List<Country> getCountriesByMovieId(int movieId) {
        return countryDao.getCountriesByMovieId(movieId);
    }
}

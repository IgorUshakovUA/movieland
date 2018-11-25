package com.ushakov.movieland.dao;

import com.ushakov.movieland.entity.Country;

import java.util.List;

public interface CountryDao {
    List<Country> getCountriesByCountryGroupId(int countryGroupId);
}

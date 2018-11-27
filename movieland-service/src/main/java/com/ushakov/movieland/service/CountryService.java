package com.ushakov.movieland.service;

import com.ushakov.movieland.entity.Country;

import java.util.List;

public interface CountryService {
    List<Country> getCountriesByMovieId(int movieId);
    List<Country> getAll();
}

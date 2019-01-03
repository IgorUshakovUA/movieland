package com.ushakov.movieland.web.controller;

import com.ushakov.movieland.entity.Country;
import com.ushakov.movieland.service.CountryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CountryController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private CountryService countryService;

    @Autowired
    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping(path = "/v1/country", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Country> getAll() {
        logger.info("Get all countries");

        return countryService.getAll();
    }

    public void setCountryService(CountryService countryService) {
        this.countryService = countryService;
    }
}

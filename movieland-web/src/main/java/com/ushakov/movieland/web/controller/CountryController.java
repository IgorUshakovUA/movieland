package com.ushakov.movieland.web.controller;

import com.ushakov.movieland.entity.Country;
import com.ushakov.movieland.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CountryController {
    private CountryService countryService;

    @Autowired
    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @RequestMapping(path = "/v1/country", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Country> getAll() {
        return countryService.getAll();
    }

    public void setCountryService(CountryService countryService) {
        this.countryService = countryService;
    }
}

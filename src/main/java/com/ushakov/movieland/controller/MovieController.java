package com.ushakov.movieland.controller;

import com.ushakov.movieland.entity.Movie;
import com.ushakov.movieland.service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MovieController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @RequestMapping(path = "/v1/movie", method = RequestMethod.GET)
    public List<Movie> getAll() {
        logger.debug("getAll was started.");

        List<Movie> movieList = movieService.getAll();

        logger.trace("movieList {}", movieList);

        return movieList;
    }
}

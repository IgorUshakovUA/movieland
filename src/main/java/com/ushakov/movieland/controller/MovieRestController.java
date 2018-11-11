package com.ushakov.movieland.controller;

import com.ushakov.movieland.entity.Movie;
import com.ushakov.movieland.service.MovieService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MovieRestController {
    private MovieService movieService;

    public MovieRestController(MovieService movieService) {
        this.movieService = movieService;
    }

    @RequestMapping(path="/v1/movie", method = RequestMethod.GET)
    public List<Movie> getAll() {
        return movieService.getAll();
    }
}

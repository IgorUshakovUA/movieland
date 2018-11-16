package com.ushakov.movieland.web.controller;

import com.ushakov.movieland.entity.Movie;
import com.ushakov.movieland.service.MovieService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MovieController {
    private MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @RequestMapping(path = "/v1/movie", method = RequestMethod.GET)
    public List<Movie> getAll() {
        return movieService.getAll();
    }

    @RequestMapping(path = "/v1/movie/random", method = RequestMethod.GET)
    public List<Movie> getThreeRandomMovies() {
        return movieService.getThreeRandomMovies();
    }

    public void setMovieService(MovieService movieService) {
        this.movieService = movieService;
    }
}

package com.ushakov.movieland.web.controller;

import com.ushakov.movieland.dao.SortField;
import com.ushakov.movieland.dao.SortType;
import com.ushakov.movieland.entity.Movie;
import com.ushakov.movieland.service.MovieService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RestController
public class MovieController {
    private MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @RequestMapping(path = "/v1/movie", method = RequestMethod.GET)
    public List<Movie> getAll() {
        return movieService.getAll();
    }

    @RequestMapping(path = "/v1/movie", params = "rating", method = RequestMethod.GET)
    public List<Movie> getAllSortedByRating(@RequestParam("rating") String rating) {
        return movieService.getAllSorted(SortField.valueOf("RATING"), SortType.valueOf(rating.trim().toUpperCase()));
    }

    @RequestMapping(path = "/v1/movie", params = "price", method = RequestMethod.GET)
    public List<Movie> getAllSortedByPrice(@RequestParam("price") String price) {
        return movieService.getAllSorted(SortField.valueOf("PRICE"), SortType.valueOf(price.trim().toUpperCase()));
    }

    @RequestMapping(path = "/v1/movie/random", method = RequestMethod.GET)
    public List<Movie> getThreeRandomMovies() {
        return movieService.getThreeRandomMovies();
    }

    @RequestMapping(path = "/v1/movie/genre/{genreId}", method = RequestMethod.GET)
    public List<Movie> getMoviesByGenre(@PathVariable int genreId) {
        return movieService.getMoviesByGenre(genreId);
    }

    @RequestMapping(path = "/v1/movie/genre/{genreId}", params = "rating", method = RequestMethod.GET)
    public List<Movie> getMoviesByGenreSortedByRating(@PathVariable int genreId, @RequestParam("rating") String rating) {
        return movieService.getMoviesByGenreSorted(genreId, SortField.valueOf("RATING"), SortType.valueOf(rating.trim().toUpperCase()));
    }

    @RequestMapping(path = "/v1/movie/genre/{genreId}", params = "price", method = RequestMethod.GET)
    public List<Movie> getMoviesByGenreSortedByPrice(@PathVariable int genreId, @RequestParam("price") String price) {
        return movieService.getMoviesByGenreSorted(genreId, SortField.valueOf("PRICE"), SortType.valueOf(price.trim().toUpperCase()));
    }

    public void setMovieService(MovieService movieService) {
        this.movieService = movieService;
    }
}

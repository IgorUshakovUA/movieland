package com.ushakov.movieland.service;

import com.ushakov.movieland.entity.Movie;

import java.util.List;

public interface MovieService {
    List<Movie> getAll();
    List<Movie> getThreeRandomMovies();
    List<Movie> getMoviesByGenre(int genreId);
}

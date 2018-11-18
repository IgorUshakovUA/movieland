package com.ushakov.movieland.dao;

import com.ushakov.movieland.entity.Movie;

import java.util.List;

public interface MovieDao {
    List<Movie> getAll();
    List<Movie> getThreeRandomMovies();
    List<Movie> getMoviesByGenre(int genreId);
}

package com.ushakov.movieland.dao;

import com.ushakov.movieland.entity.Movie;

import java.util.List;

public interface MovieDao {
    List<Movie> getAll();
    List<Movie> getAllSorted(SortField sortField, SortType sortType);
    List<Movie> getThreeRandomMovies();
    List<Movie> getMoviesByGenre(int genreId);
    List<Movie> getMoviesByGenreSorted(int genreId, SortField sortField, SortType sortType);
}

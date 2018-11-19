package com.ushakov.movieland.service;

import com.ushakov.movieland.dao.SortField;
import com.ushakov.movieland.dao.SortType;
import com.ushakov.movieland.entity.Movie;

import java.util.List;

public interface MovieService {
    List<Movie> getAll();
    List<Movie> getAllSorted(SortField sortField, SortType sortType);
    List<Movie> getThreeRandomMovies();
    List<Movie> getMoviesByGenre(int genreId);
    List<Movie> getMoviesByGenreSorted(int genreId, SortField sortField, SortType sortType);
}

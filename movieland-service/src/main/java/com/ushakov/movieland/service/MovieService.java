package com.ushakov.movieland.service;

import com.ushakov.movieland.common.RequestSearchParam;
import com.ushakov.movieland.entity.Movie;

import java.util.List;

public interface MovieService {
    List<Movie> getAll(RequestSearchParam requestSearchParam);
    List<Movie> getThreeRandomMovies();
    List<Movie> getMoviesByGenre(int genreId, RequestSearchParam requestSearchParam);
}

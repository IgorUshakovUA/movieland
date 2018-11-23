package com.ushakov.movieland.dao;

import com.ushakov.movieland.common.RequestSearchParam;
import com.ushakov.movieland.entity.Movie;

import java.util.List;

public interface MovieDao {
    List<Movie> getAll(RequestSearchParam requestSearchParam);
    List<Movie> getThreeRandomMovies();
    List<Movie> getMoviesByGenre(int genreId, RequestSearchParam requestSearchParam);
}

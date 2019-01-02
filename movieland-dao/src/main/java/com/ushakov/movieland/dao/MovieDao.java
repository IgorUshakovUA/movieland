package com.ushakov.movieland.dao;

import com.ushakov.movieland.common.RequestSearchParam;
import com.ushakov.movieland.entity.Movie;
import com.ushakov.movieland.entity.MovieDetailed;

import java.util.List;

public interface MovieDao {
    List<Movie> getAll(RequestSearchParam requestSearchParam);
    List<Movie> getThreeRandomMovies();
    List<Movie> getMoviesByGenre(int genreId, RequestSearchParam requestSearchParam);
    MovieDetailed getMovieById(int id);
    double getUserRatingByMovieId(int userId, int movieId);
}

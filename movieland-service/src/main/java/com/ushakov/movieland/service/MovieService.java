package com.ushakov.movieland.service;

import com.ushakov.movieland.common.RequestSearchParam;
import com.ushakov.movieland.entity.Movie;
import com.ushakov.movieland.entity.MovieDetailed;

import java.util.List;

public interface MovieService {
    List<Movie> getAll();
    List<Movie> getAll(RequestSearchParam requestSearchParam);
    List<Movie> getThreeRandomMovies();
    List<Movie> getMoviesByGenre(int genreId);
    List<Movie> getMoviesByGenre(int genreId, RequestSearchParam requestSearchParam);
    MovieDetailed getMovieById(int id);
    MovieDetailed getMovieById(int id, RequestSearchParam requestSearchParam);
    int updateMovie(Movie movie);
    int insertMovie(Movie movie);
    double getUserRatingByMovieId(int userId, int movieId);
}

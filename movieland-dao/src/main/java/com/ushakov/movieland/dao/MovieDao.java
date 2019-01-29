package com.ushakov.movieland.dao;

import com.ushakov.movieland.common.RequestSearchParam;
import com.ushakov.movieland.entity.Movie;
import com.ushakov.movieland.entity.MovieDetailed;
import com.ushakov.movieland.entity.NewMovie;

import java.util.List;

public interface MovieDao {
    List<Movie> getAll(RequestSearchParam requestSearchParam);
    List<Movie> getThreeRandomMovies();
    List<Movie> getMoviesByGenre(int genreId, RequestSearchParam requestSearchParam);
    MovieDetailed getMovieById(int id);
    int updateMovie(NewMovie movie);
    int insertMovie(NewMovie movie);
    double getUserRatingByMovieId(int userId, int movieId);
}

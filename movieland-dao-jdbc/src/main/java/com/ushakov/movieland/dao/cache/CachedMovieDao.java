package com.ushakov.movieland.dao.cache;

import com.ushakov.movieland.common.RequestSearchParam;
import com.ushakov.movieland.dao.MovieDao;
import com.ushakov.movieland.entity.Movie;
import com.ushakov.movieland.entity.MovieDetailed;
import com.ushakov.movieland.entity.NewMovie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Primary
@Repository
public class CachedMovieDao implements MovieDao {
    private ConcurrentHashMap<Integer, Reference<MovieDetailed>> movies = new ConcurrentHashMap<>();
    private MovieDao movieDao;


    @Autowired
    public CachedMovieDao(@Qualifier("jdbcMovieDao") MovieDao movieDao) {
        this.movieDao = movieDao;
    }

    @Override
    public List<Movie> getAll(RequestSearchParam requestSearchParam) {
        return movieDao.getAll(requestSearchParam);
    }

    @Override
    public List<Movie> getThreeRandomMovies() {
        return movieDao.getThreeRandomMovies();
    }

    @Override
    public List<Movie> getMoviesByGenre(int genreId, RequestSearchParam requestSearchParam) {
        return movieDao.getMoviesByGenre(genreId, requestSearchParam);
    }

    @Override
    public MovieDetailed getMovieById(int id) {
        MovieDetailed result;

        Reference<MovieDetailed> reference = movies.get(id);

        if (reference != null && (result = reference.get()) != null) {
            return result;
        }

        result = movieDao.getMovieById(id);

        reference = new SoftReference<>(result);

        movies.put(result.getId(), reference);

        return result;
    }

    @Override
    public int updateMovie(NewMovie movie) {
        int movieId = movieDao.updateMovie(movie);

        MovieDetailed updatedMovie = movieDao.getMovieById(movieId);

        Reference<MovieDetailed> reference = new SoftReference<>(updatedMovie);

        movies.put(movieId, reference);

        return movieId;
    }

    @Override
    public int insertMovie(NewMovie movie) {
        int movieId = movieDao.insertMovie(movie);

        MovieDetailed newMovie = movieDao.getMovieById(movieId);

        Reference<MovieDetailed> reference = new SoftReference<>(newMovie);

        movies.put(movieId, reference);

        return movieId;
    }

    @Override
    public double getUserRatingByMovieId(int userId, int movieId) {
        return movieDao.getUserRatingByMovieId(userId, movieId);
    }
}

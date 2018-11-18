package com.ushakov.movieland.service;

import com.ushakov.movieland.dao.MovieDao;
import com.ushakov.movieland.entity.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultMovieService implements MovieService {
    private MovieDao movieDao;

    @Autowired
    public DefaultMovieService(MovieDao movieDao) {
        this.movieDao = movieDao;
    }

    @Override
    public List<Movie> getAll() {
        return movieDao.getAll();
    }

    @Override
    public List<Movie> getThreeRandomMovies() {
        return movieDao.getThreeRandomMovies();
    }

    @Override
    public List<Movie> getMoviesByGenre(int genreId) {
        return movieDao.getMoviesByGenre(genreId);
    }
}

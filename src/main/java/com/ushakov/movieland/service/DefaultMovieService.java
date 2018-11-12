package com.ushakov.movieland.service;

import com.ushakov.movieland.dao.MovieDao;
import com.ushakov.movieland.entity.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class DefaultMovieService implements MovieService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private MovieDao movieDao;

    public DefaultMovieService(MovieDao movieDao) {
        this.movieDao = movieDao;
    }

    @Override
    public List<Movie> getAll() {
        List<Movie> movieList = movieDao.getAll();
        Collections.sort(movieList, Comparator.comparingInt(Movie::getId));

        logger.trace("movieList {}", movieList);

        return movieList;
    }
}

package com.ushakov.movieland.dao.jdbc;

import com.ushakov.movieland.dao.MovieDao;
import com.ushakov.movieland.dao.jdbc.mapper.MovieRowMapper;
import com.ushakov.movieland.entity.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcMovieDao implements MovieDao {
    private static final String GET_ALL_SQL = "SELECT movie.id, movie.nameRussian, movie.nameNative, movie.yearOfRelease, movie.rating, movie.price, poster.picturePath FROM movie, poster WHERE movie.posterId = poster.id";
    private static final MovieRowMapper MOVIE_ROW_MAPPER = new MovieRowMapper();
    private final Logger logger = LoggerFactory.getLogger(getClass());


    private JdbcTemplate jdbcTemplate;


    public JdbcMovieDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Movie> getAll() {
        List<Movie> movieList = jdbcTemplate.query(GET_ALL_SQL, MOVIE_ROW_MAPPER);

        logger.trace("movieList {}", movieList);

        return movieList;
    }
}

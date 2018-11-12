package com.ushakov.movieland.dao.jdbc;

import com.ushakov.movieland.dao.MovieDao;
import com.ushakov.movieland.dao.jdbc.mapper.MovieRowMapper;
import com.ushakov.movieland.entity.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JdbcMovieDao implements MovieDao {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    static final String GET_ALL_SQL = "SELECT movie.id, movie.nameRussian, movie.nameNative, movie.yearOfRelease, movie.rating, movie.price, poster.picturePath FROM movie, poster WHERE movie.posterId = poster.id";
    static final MovieRowMapper MOVIE_ROW_MAPPER = new MovieRowMapper();

    private JdbcTemplate jdbcTemplate;


    public JdbcMovieDao(JdbcTemplate jdbcTemplate) {
        logger.info("JdbcMovieDao was created.");

        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Movie> getAll() {
        logger.info("JdbcMovieDao.getAll was started.");

        return jdbcTemplate.query(GET_ALL_SQL, MOVIE_ROW_MAPPER);
    }
}

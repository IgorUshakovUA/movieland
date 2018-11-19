package com.ushakov.movieland.dao.jdbc;

import com.ushakov.movieland.dao.MovieDao;
import com.ushakov.movieland.dao.SortField;
import com.ushakov.movieland.dao.SortType;
import com.ushakov.movieland.dao.jdbc.mapper.MovieRowMapper;
import com.ushakov.movieland.entity.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcMovieDao implements MovieDao {
    private static final String GET_ALL_SQL = "SELECT movie.id, movie.nameRussian, movie.nameNative, movie.yearOfRelease, movie.rating, movie.price, poster.picturePath FROM movie, poster WHERE movie.posterId = poster.id";
    private static final String GET_THREE_MOVIES_BY_IDS = "SELECT movie.id, movie.nameRussian, movie.nameNative, movie.yearOfRelease, movie.rating, movie.price, poster.picturePath FROM movie, poster WHERE movie.posterId = poster.id AND RANDOM() < 0.5 LIMIT 3";
    private static final String GET_MOVIES_BY_GENRE_SQL = "SELECT movie.id, movie.nameRussian, movie.nameNative, movie.yearOfRelease, movie.rating, movie.price, poster.picturePath FROM movie, poster, genreGroup WHERE movie.posterId = poster.id AND movie.genreGroupId = genreGroup.id AND genreGroup.genreId = ?";
    private static final MovieRowMapper MOVIE_ROW_MAPPER = new MovieRowMapper();
    private final Logger logger = LoggerFactory.getLogger(getClass());


    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcMovieDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Movie> getAll() {
        List<Movie> movieList = jdbcTemplate.query(GET_ALL_SQL, MOVIE_ROW_MAPPER);

        logger.trace("All movies: {}", movieList);

        return movieList;
    }

    @Override
    public List<Movie> getAllSorted(SortField sortField, SortType sortType) {
        StringBuilder query = new StringBuilder(GET_ALL_SQL);
        query.append(" ORDER BY ");
        query.append(sortField.value());
        query.append(" ");
        query.append(sortType.value());

        List<Movie> movieList = jdbcTemplate.query(query.toString(), MOVIE_ROW_MAPPER);

        logger.trace("All movies sorted by {} ordered {}: {}", sortField, sortType, movieList);

        return movieList;
    }

    @Override
    public List<Movie> getThreeRandomMovies() {
        List<Movie> movieList = jdbcTemplate.query(GET_THREE_MOVIES_BY_IDS, MOVIE_ROW_MAPPER);

        logger.trace("Three random movies: {}", movieList);

        return movieList;
    }

    @Override
    public List<Movie> getMoviesByGenre(int genreId) {
        List<Movie> movieList = jdbcTemplate.query(GET_MOVIES_BY_GENRE_SQL, MOVIE_ROW_MAPPER, genreId);

        logger.trace("Movies by genreId = {}: {}", genreId, movieList);

        return movieList;
    }

    @Override
    public List<Movie> getMoviesByGenreSorted(int genreId, SortField sortField, SortType sortType) {
        StringBuilder query = new StringBuilder(GET_MOVIES_BY_GENRE_SQL);
        query.append(" ORDER BY ");
        query.append(sortField.value());
        query.append(" ");
        query.append(sortType.value());

        List<Movie> movieList = jdbcTemplate.query(query.toString(), MOVIE_ROW_MAPPER, genreId);

        logger.trace("Movies by genreId = {} sorted by {} ordered {}: {}", genreId, sortField, sortType, movieList);

        return movieList;
    }
}

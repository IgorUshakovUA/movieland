package com.ushakov.movieland.dao.jdbc;

import com.ushakov.movieland.common.RequestSearchParam;
import com.ushakov.movieland.dao.MovieDao;
import com.ushakov.movieland.common.SortField;
import com.ushakov.movieland.common.SortType;
import com.ushakov.movieland.dao.jdbc.mapper.MovieDetailedRowMapper;
import com.ushakov.movieland.dao.jdbc.mapper.MovieRowMapper;
import com.ushakov.movieland.entity.Movie;
import com.ushakov.movieland.entity.MovieDetailed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcMovieDao implements MovieDao {
    private static final String GET_ALL_SQL = "SELECT movie.id, movie.nameRussian, movie.nameNative, movie.yearOfRelease, movie.rating, movie.price, poster.picturePath, movie.description FROM movie, poster WHERE movie.posterId = poster.id";
    private static final String GET_THREE_MOVIES_BY_IDS = "SELECT movie.id, movie.nameRussian, movie.nameNative, movie.yearOfRelease, movie.rating, movie.price, poster.picturePath, movie.description FROM movie, poster WHERE movie.posterId = poster.id AND RANDOM() < 0.5 LIMIT 3";
    private static final String GET_MOVIES_BY_GENRE_SQL = "SELECT movie.id, movie.nameRussian, movie.nameNative, movie.yearOfRelease, movie.rating, movie.price, poster.picturePath, movie.description FROM movie, poster, genreGroup WHERE movie.posterId = poster.id AND movie.genreGroupId = genreGroup.id AND genreGroup.genreId = ?";
    private static final String GET_MOVIE_BY_ID_SQL = "SELECT movie.id, movie.nameRussian, movie.nameNative, movie.yearOfRelease, movie.rating, movie.price, poster.picturePath, movie.description, movie.genreGroupId, movie.countryGroupId FROM movie, poster WHERE movie.posterId = poster.id AND movie.id = ?";
    private static final String GET_MOVIE_RATING_BY_USER_ID = "SELECT MAX(r.rating) rating FROM (SELECT 0.0 AS rating UNION ALL SELECT rating FROM userRating WHERE userId = ? AND movieId = ?) r";
    private static final MovieRowMapper MOVIE_ROW_MAPPER = new MovieRowMapper();
    private static final MovieDetailedRowMapper MOVIE_DETAILED_ROW_MAPPER = new MovieDetailedRowMapper();
    private final Logger logger = LoggerFactory.getLogger(getClass());


    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcMovieDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Movie> getAll(RequestSearchParam requestSearchParam) {
        SortField sortField;
        SortType sortType;
        String query;

        if (requestSearchParam == null || (sortField = requestSearchParam.getSortField()) == null || (sortType = requestSearchParam.getSortType()) == null) {
            query = GET_ALL_SQL;
        } else {
            logger.debug("Sorting field = {}, order = {}", sortField, sortType);

            query = buildSortedQuery(GET_ALL_SQL, sortField, sortType);
        }

        List<Movie> movieList = jdbcTemplate.query(query, MOVIE_ROW_MAPPER);

        logger.debug("All movies, size: {}", movieList.size());
        logger.trace("Movies: {}", movieList);

        return movieList;
    }

    @Override
    public List<Movie> getThreeRandomMovies() {
        List<Movie> movieList = jdbcTemplate.query(GET_THREE_MOVIES_BY_IDS, MOVIE_ROW_MAPPER);

        logger.debug("Three random movies, size: {}", movieList.size());
        logger.trace("Movies: {}", movieList);

        return movieList;
    }

    @Override
    public List<Movie> getMoviesByGenre(int genreId, RequestSearchParam requestSearchParam) {
        SortField sortField;
        SortType sortType;
        String query;

        if (requestSearchParam == null || (sortField = requestSearchParam.getSortField()) == null || (sortType = requestSearchParam.getSortType()) == null) {
            query = GET_MOVIES_BY_GENRE_SQL;
        } else {
            logger.debug("Sorting field = {}, order = {}", sortField, sortType);

            query = buildSortedQuery(GET_MOVIES_BY_GENRE_SQL, sortField, sortType);
        }

        List<Movie> movieList = jdbcTemplate.query(query, MOVIE_ROW_MAPPER, genreId);

        logger.debug("Movies by genreId = {} size: {}", genreId, movieList.size());
        logger.trace("Movies: {}", movieList);

        return movieList;
    }

    @Override
    public MovieDetailed getMovieById(int id) {
        MovieDetailed movieDetailed = jdbcTemplate.queryForObject(GET_MOVIE_BY_ID_SQL, MOVIE_DETAILED_ROW_MAPPER, id);

        logger.debug("Movie: {}", movieDetailed);

        return movieDetailed;
    }

    @Override
    public double getUserRatingByMovieId(int userId, int movieId) {
        return jdbcTemplate.queryForObject(GET_MOVIE_RATING_BY_USER_ID, Double.class, userId, movieId);
    }

    static String buildSortedQuery(String baseQuery, SortField sortField, SortType sortType) {
        StringBuilder query = new StringBuilder(baseQuery);
        query.append(" ORDER BY ");
        query.append(sortField.value());
        query.append(" ");
        query.append(sortType.value());

        return query.toString();
    }
}

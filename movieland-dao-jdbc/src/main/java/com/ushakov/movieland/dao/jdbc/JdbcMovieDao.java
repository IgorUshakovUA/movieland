package com.ushakov.movieland.dao.jdbc;

import com.ushakov.movieland.common.RequestSearchParam;
import com.ushakov.movieland.dao.MovieDao;
import com.ushakov.movieland.common.SortField;
import com.ushakov.movieland.common.SortType;
import com.ushakov.movieland.dao.jdbc.mapper.MovieDetailedRowMapper;
import com.ushakov.movieland.dao.jdbc.mapper.MovieRowMapper;
import com.ushakov.movieland.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Array;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Repository
@Transactional(isolation = Isolation.READ_COMMITTED)
public class JdbcMovieDao implements MovieDao {
    private static final String GET_ALL_SQL = "SELECT movie.id, movie.nameRussian, movie.nameNative, movie.yearOfRelease, movie.rating, movie.price, poster.picturePath, movie.description FROM movie, poster WHERE movie.id = poster.id";
    private static final String GET_THREE_MOVIES_BY_IDS = "SELECT movie.id, movie.nameRussian, movie.nameNative, movie.yearOfRelease, movie.rating, movie.price, poster.picturePath, movie.description FROM movie, poster WHERE movie.id = poster.id AND RANDOM() < 0.5 LIMIT 3";
    private static final String GET_MOVIES_BY_GENRE_SQL = "SELECT movie.id, movie.nameRussian, movie.nameNative, movie.yearOfRelease, movie.rating, movie.price, poster.picturePath, movie.description FROM movie, poster, genreGroup WHERE movie.id = poster.id AND movie.genreGroupId = genreGroup.id AND genreGroup.genreId = ?";
    private static final String GET_MOVIE_BY_ID_SQL = "SELECT movie.id, movie.nameRussian, movie.nameNative, movie.yearOfRelease, movie.rating, movie.price, poster.picturePath, movie.description, movie.genreGroupId, movie.countryGroupId FROM movie, poster WHERE movie.id = poster.id AND movie.id = ?";
    private static final String GET_MOVIE_RATING_BY_USER_ID = "SELECT MAX(r.rating) rating FROM (SELECT 0.0 AS rating UNION ALL SELECT rating FROM userRating WHERE userId = ? AND movieId = ?) r";
    private static final String INSERT_NEW_MOVIE_SQL = "SELECT insert_movie(?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_MOVIE_BY_ID_SQL = "SELECT update_movie(?, ?, ?, ?, ?, ?)";
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
    public int updateMovie(NewMovie movie) {
        return jdbcTemplate.queryForObject(UPDATE_MOVIE_BY_ID_SQL, Integer.class, movie.getId(), movie.getNameRussian(),
                movie.getNameNative(), movie.getPicturePath(), idListToIntArray(movie.getCountries()),
                idListToIntArray(movie.getGenres()));
    }

    @Override
    public int insertMovie(NewMovie movie) {
        return jdbcTemplate.queryForObject(INSERT_NEW_MOVIE_SQL, Integer.class, movie.getNameRussian(), movie.getNameNative(),
                movie.getYearOfRelease(), movie.getDescription(), movie.getPrice(), movie.getPicturePath(),
                idListToIntArray(movie.getCountries()), idListToIntArray(movie.getGenres()));
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

    Array idListToIntArray(List<Integer> ids) {
        if(ids == null) {
            return null;
        }

        try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            return connection.createArrayOf("INTEGER", ids.toArray());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

package com.ushakov.movieland.dao.jdbc.mapper;

import com.ushakov.movieland.dao.CountryDao;
import com.ushakov.movieland.dao.GenreDao;
import com.ushakov.movieland.dao.ReviewDao;
import com.ushakov.movieland.entity.Country;
import com.ushakov.movieland.entity.Genre;
import com.ushakov.movieland.entity.MovieDetailed;
import com.ushakov.movieland.entity.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class MovieDetailedRowMapper implements RowMapper<MovieDetailed> {

    private CountryDao countryDao;
    private GenreDao genreDao;
    private ReviewDao reviewDao;

    @Autowired
    public MovieDetailedRowMapper(CountryDao countryDao, GenreDao genreDao, ReviewDao reviewDao) {
        this.countryDao = countryDao;
        this.genreDao = genreDao;
        this.reviewDao = reviewDao;
    }

    @Override
    public MovieDetailed mapRow(ResultSet resultSet, int i) throws SQLException {
        List<Country> countryList = countryDao.getCountriesByCountryGroupId(resultSet.getInt("countryGroupId"));
        List<Genre> genreList = genreDao.getGenresGenreGroupId(resultSet.getInt("genreGroupId"));
        int movieId = resultSet.getInt("id");
        List<Review> reviewList = reviewDao.getReviewesByMovieId(movieId);
        MovieDetailed movieDetailed = new MovieDetailed();
        movieDetailed.setId(movieId);
        movieDetailed.setNameRussian(resultSet.getString("nameRussian"));
        movieDetailed.setNameNative(resultSet.getString("nameNative"));
        movieDetailed.setYearOfRelease(resultSet.getInt("yearOfRelease"));
        movieDetailed.setDescritpion(resultSet.getString("description"));
        movieDetailed.setRating(resultSet.getDouble("rating"));
        movieDetailed.setPrice(resultSet.getDouble("price"));
        movieDetailed.setPicturePath(resultSet.getString("picturePath"));
        movieDetailed.setCountries(countryList);
        movieDetailed.setGenres(genreList);
        movieDetailed.setReviews(reviewList);

        return movieDetailed;
    }
}

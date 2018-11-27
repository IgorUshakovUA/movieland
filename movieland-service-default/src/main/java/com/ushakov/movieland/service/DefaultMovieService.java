package com.ushakov.movieland.service;

import com.ushakov.movieland.common.RequestSearchParam;
import com.ushakov.movieland.dao.MovieDao;
import com.ushakov.movieland.entity.Movie;
import com.ushakov.movieland.entity.MovieDetailed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultMovieService implements MovieService {
    private MovieDao movieDao;
    private CountryService countryService;
    private GenreService genreService;
    private ReviewService reviewService;
    private CurrencyService currencyService;

    @Autowired
    public DefaultMovieService(MovieDao movieDao, CountryService countryService, GenreService genreService, ReviewService reviewService, CurrencyService currencyService) {
        this.movieDao = movieDao;
        this.countryService = countryService;
        this.genreService = genreService;
        this.reviewService = reviewService;
        this.currencyService = currencyService;
    }

    @Override
    public List<Movie> getAll() {
        return getAll(null);
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
    public List<Movie> getMoviesByGenre(int genreId) {
        return getMoviesByGenre(genreId, null);
    }

    @Override
    public List<Movie> getMoviesByGenre(int genreId, RequestSearchParam requestSearchParam) {
        return movieDao.getMoviesByGenre(genreId, requestSearchParam);
    }

    @Override
    public MovieDetailed getMovieById(int id) {
        return getMovieById(id, null);
    }

    @Override
    public MovieDetailed getMovieById(int id, RequestSearchParam requestSearchParam) {
        MovieDetailed movieDetailed = movieDao.getMovieById(id);
        movieDetailed.setCountries(countryService.getCountriesByMovieId(id));
        movieDetailed.setGenres(genreService.getGenresByMovieId(id));
        movieDetailed.setReviews(reviewService.getReviewsByMovieId(id));
        if (requestSearchParam != null && requestSearchParam.getCurrency() != null) {
            double value = movieDetailed.getPrice() / currencyService.getCurrencyRate(requestSearchParam.getCurrency());
            value = Math.floor(value * 100) / 100;
            movieDetailed.setPrice(value);
        }
        return movieDetailed;
    }
}

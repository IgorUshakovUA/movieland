package com.ushakov.movieland.service;

import com.ushakov.movieland.callable.EnrichCallable;
import com.ushakov.movieland.common.RequestSearchParam;
import com.ushakov.movieland.dao.MovieDao;
import com.ushakov.movieland.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

@Service
public class DefaultMovieService implements MovieService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private MovieDao movieDao;
    private CountryService countryService;
    private GenreService genreService;
    private ReviewService reviewService;
    private CurrencyService currencyService;
    private ExecutorService executorService;

    @Value("${executor.timeout-millis}")
    private int executorTimeoutMillis = 5000;

    @Autowired
    public DefaultMovieService(MovieDao movieDao, CountryService countryService, GenreService genreService, ReviewService reviewService, CurrencyService currencyService, ExecutorService executorService) {
        this.movieDao = movieDao;
        this.countryService = countryService;
        this.genreService = genreService;
        this.reviewService = reviewService;
        this.currencyService = currencyService;
        this.executorService = executorService;
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

        List<EnrichCallable<List<Object>>> callableList = Arrays.asList(
                new EnrichCallable(id, countryService::getCountriesByMovieId),
                new EnrichCallable(id, genreService::getGenresByMovieId),
                new EnrichCallable(id, reviewService::getReviewsByMovieId));

        try {

            List<Future<List<Object>>> futureList = executorService.invokeAll(callableList, executorTimeoutMillis, TimeUnit.MILLISECONDS);

            Future<List<Object>> countryFuture = futureList.get(0);
            if (countryFuture.isDone()) {
                try {
                    movieDetailed.setCountries(convertEntityList(countryFuture.get()));
                } catch (ExecutionException e) {
                    logger.warn("Cannot enrich countries for movieId: {}, because an exception happened: ", id, e);
                }
            }

            Future<List<Object>> genresFuture = futureList.get(1);
            if (genresFuture.isDone()) {
                try {
                    movieDetailed.setGenres(convertEntityList(genresFuture.get()));
                } catch (ExecutionException e) {
                    logger.warn("Cannot enrich genres for movieId: {}, because an exception happened: ", id, e);
                }
            }

            Future<List<Object>> reviewFuture = futureList.get(2);
            if (reviewFuture.isDone()) {
                try {
                    movieDetailed.setReviews(convertEntityList(reviewFuture.get()));
                } catch (ExecutionException e) {
                    logger.warn("Cannot enrich reviews for movieId: {}, because an exception happened: ", id, e);
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (requestSearchParam != null && requestSearchParam.getCurrency() != null) {
            movieDetailed.setPrice(movieDetailed.getPrice() / currencyService.getCurrencyRate(requestSearchParam.getCurrency()));
        }

        return movieDetailed;
    }

    private static <T> List<T> convertEntityList(List<Object> entityList) {
        List<T> tList = new ArrayList<>();

        for (Object entity : entityList) {
            tList.add((T) entity);
        }

        return tList;
    }
}

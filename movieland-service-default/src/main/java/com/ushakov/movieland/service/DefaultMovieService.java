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
        List<Movie> movieList = movieDao.getThreeRandomMovies();

        List<EnrichCallable<List<Object>>> callableList = new ArrayList<>();

        for (Movie movie : movieList) {
            callableList.add(new EnrichCallable(movie.getId(), countryService::getCountriesByMovieId));
            callableList.add(new EnrichCallable(movie.getId(), genreService::getGenresByMovieId));
        }

        try {
            List<Future<List<Object>>> futureList = executorService.invokeAll(callableList, executorTimeoutMillis, TimeUnit.MILLISECONDS);

            for (int i = 0; i < futureList.size(); i++) {
                Future<List<Object>> future = futureList.get(i);
                if (future.isDone()) {
                    Movie movie = movieList.get(i / 2);
                    if ((i + 1) % 2 == 1) {
                        try {
                            movie.setCountries(convertEntityList(future.get()));
                        } catch (ExecutionException e) {
                            logger.warn("Cannot enrich countries for movieId: {}, because an exception happened: ", movie.getId(), e);
                        }
                    } else {
                        try {
                            movie.setGenres(convertEntityList(future.get()));
                        } catch (ExecutionException e) {
                            logger.warn("Cannot enrich genres for movieId: {}, because an exception happened: ", movie.getId(), e);
                        }
                    }
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return movieList;
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

    @Override
    public int updateMovie(NewMovie movie) {
        return movieDao.updateMovie(movie);
    }

    @Override
    public int insertMovie(NewMovie movie) {
        return movieDao.insertMovie(movie);
    }

    @Override
    public double getUserRatingByMovieId(int userId, int movieId) {
        return movieDao.getUserRatingByMovieId(userId, movieId);
    }

    private static <T> List<T> convertEntityList(List<Object> entityList) {
        List<T> tList = new ArrayList<>();

        for (Object entity : entityList) {
            tList.add((T) entity);
        }

        return tList;
    }
}

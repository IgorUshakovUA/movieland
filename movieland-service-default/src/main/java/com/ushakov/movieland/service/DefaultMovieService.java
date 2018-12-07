package com.ushakov.movieland.service;

import com.ushakov.movieland.common.RequestSearchParam;
import com.ushakov.movieland.dao.MovieDao;
import com.ushakov.movieland.entity.*;
import com.ushakov.movieland.futuretask.EnrichFutureTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

@Service
public class DefaultMovieService implements MovieService {
    private MovieDao movieDao;
    private CountryService countryService;
    private GenreService genreService;
    private ReviewService reviewService;
    private CurrencyService currencyService;
    private ExecutorService executorService;

    @Value("${executor.timeout-millis}")
    private int executorTimeoutMillis = 5000;
    @Value("${executor.sleep-millis}")
    private int executorSleepMillis = 50;

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

        EnrichFutureTask<List<Country>> countriesFutureTask = new EnrichFutureTask<>(id, countryService::getCountriesByMovieId);
        EnrichFutureTask<List<Genre>> genresFutureTask = new EnrichFutureTask<>(id, genreService::getGenresByMovieId);
        EnrichFutureTask<List<Review>> reviewesFutureTask = new EnrichFutureTask<>(id, reviewService::getReviewsByMovieId);

        executorService.submit(countriesFutureTask);
        executorService.submit(genresFutureTask);
        executorService.submit(reviewesFutureTask);

        int waitTime = 0;
        boolean isCountriesEnriched = false;
        boolean isGenresEnriched = false;
        boolean isReviewesEnriched = false;

        while (waitTime < executorTimeoutMillis && (!isCountriesEnriched || !isGenresEnriched || !isReviewesEnriched)) {
            try {
                Thread.sleep(executorTimeoutMillis);

                waitTime += executorSleepMillis;

                if(!isCountriesEnriched && countriesFutureTask.isDone()) {
                    isCountriesEnriched = true;
                    movieDetailed.setCountries(countriesFutureTask.get());
                }

                if(!isGenresEnriched && genresFutureTask.isDone()) {
                    isGenresEnriched = true;
                    movieDetailed.setGenres(genresFutureTask.get());
                }

                if(!isReviewesEnriched && reviewesFutureTask.isDone()) {
                    isReviewesEnriched = true;
                    movieDetailed.setReviews(reviewesFutureTask.get());
                }

            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }

        if(!isCountriesEnriched) {
            countriesFutureTask.cancel(true);
        }

        if(!isGenresEnriched) {
            genresFutureTask.cancel(true);
        }

        if(!isReviewesEnriched) {
            reviewesFutureTask.cancel(true);
        }

        if (requestSearchParam != null && requestSearchParam.getCurrency() != null) {
            movieDetailed.setPrice(movieDetailed.getPrice() / currencyService.getCurrencyRate(requestSearchParam.getCurrency()));
        }

        return movieDetailed;
    }
}

package com.ushakov.movieland.web.controller;

import com.ushakov.movieland.common.*;
import com.ushakov.movieland.entity.Movie;
import com.ushakov.movieland.entity.MovieDetailed;
import com.ushakov.movieland.service.MovieService;
import com.ushakov.movieland.web.interceptor.ProtectedBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.ExecutorService;

@RestController
public class MovieController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService, ExecutorService executorService) {
        this.movieService = movieService;
    }

    @RequestMapping(path = "/v1/movie", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Movie> getAll(@RequestParam(name = "rating", required = false) SortType ratingOrder, @RequestParam(name = "price", required = false) SortType priceOrder) {
        logger.info("Get all movies.");

        if (ratingOrder == null && priceOrder == null) {
            return movieService.getAll();
        }

        return movieService.getAll(getRequestSearchParam(ratingOrder, priceOrder));
    }

    @RequestMapping(path = "/v1/movie/{movieId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public MovieDetailed getMovieById(@PathVariable int movieId, @RequestParam(name = "currency", required = false) Currency currency) {
        logger.info("Get a movie by id: {}", movieId);

        if (currency != null) {
            RequestSearchParam requestSearchParam = new RequestSearchParam();
            requestSearchParam.setCurrency(currency);
            return movieService.getMovieById(movieId, requestSearchParam);
        } else {
            return movieService.getMovieById(movieId);
        }
    }

    @ProtectedBy({UserRole.USER, UserRole.ADMIN})
    @RequestMapping(path = "/v1/movie/{movieId}/rating", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public double getUserRatingByMovieId(@PathVariable int movieId) {
        return 0.0;
    }

    @RequestMapping(path = "/v1/movie/random", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Movie> getThreeRandomMovies() {
        logger.info("Get three random movies.");

        return movieService.getThreeRandomMovies();
    }

    @RequestMapping(path = "/v1/movie/genre/{genreId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Movie> getMoviesByGenre(@PathVariable int genreId, @RequestParam(name = "rating", required = false) SortType ratingOrder, @RequestParam(name = "price", required = false) SortType priceOrder) {
        logger.info("Get movies by genreId: {}.", genreId);

        if (ratingOrder == null && priceOrder == null) {
            return movieService.getMoviesByGenre(genreId);
        }

        return movieService.getMoviesByGenre(genreId, getRequestSearchParam(ratingOrder, priceOrder));
    }

    private RequestSearchParam getRequestSearchParam(SortType ratingOrder, SortType priceOrder) {
        if (ratingOrder != null && ratingOrder == SortType.ASC) {
            throw new BadRequestException("Rating order sort order can be only DESC!");
        }

        if (ratingOrder != null && priceOrder != null) {
            throw new BadRequestException("Rating and price order sorts cannot be used together!");
        }

        RequestSearchParam requestSearchParam = new RequestSearchParam();
        if (ratingOrder != null) {
            requestSearchParam.setSortField(SortField.RATING);
            requestSearchParam.setSortType(ratingOrder);
        } else {
            requestSearchParam.setSortField(SortField.PRICE);
            requestSearchParam.setSortType(priceOrder);
        }

        return requestSearchParam;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        dataBinder.registerCustomEditor(SortType.class, new SortTypeConverter());
        dataBinder.registerCustomEditor(SortField.class, new SortFieldConverter());
    }


    public void setMovieService(MovieService movieService) {
        this.movieService = movieService;
    }
}

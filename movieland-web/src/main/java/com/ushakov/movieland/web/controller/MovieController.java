package com.ushakov.movieland.web.controller;

import com.ushakov.movieland.common.*;
import com.ushakov.movieland.entity.Movie;
import com.ushakov.movieland.entity.MovieDetailed;
import com.ushakov.movieland.service.MovieService;
import com.ushakov.movieland.web.interceptor.ProtectedBy;
import com.ushakov.movieland.web.interceptor.UserHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RestController
public class MovieController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping(path = "/v1/movie", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Movie> getAll(@RequestParam(name = "rating", required = false) SortType ratingOrder, @RequestParam(name = "price", required = false) SortType priceOrder) {
        logger.info("Get all movies.");

        if (ratingOrder == null && priceOrder == null) {
            return movieService.getAll();
        }

        return movieService.getAll(getRequestSearchParam(ratingOrder, priceOrder));
    }

    @GetMapping(path = "/v1/movie/{movieId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
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
    @GetMapping(path = "/v1/movie/{movieId}/rating", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public double getUserRatingByMovieId(@PathVariable int movieId) {
        int userId = UserHandler.getCurrentUser().getId();

        logger.info("Get rating by movieId: {} and userId: {}", movieId, userId);

        return movieService.getUserRatingByMovieId(userId, movieId);
    }

    @GetMapping(path = "/v1/movie/random", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Movie> getThreeRandomMovies() {
        logger.info("Get three random movies.");

        return movieService.getThreeRandomMovies();
    }

    @GetMapping(path = "/v1/movie/genre/{genreId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Movie> getMoviesByGenre(@PathVariable int genreId, @RequestParam(name = "rating", required = false) SortType ratingOrder, @RequestParam(name = "price", required = false) SortType priceOrder) {
        logger.info("Get movies by genreId: {}.", genreId);

        if (ratingOrder == null && priceOrder == null) {
            return movieService.getMoviesByGenre(genreId);
        }

        return movieService.getMoviesByGenre(genreId, getRequestSearchParam(ratingOrder, priceOrder));
    }

    @ProtectedBy({UserRole.ADMIN})
    @PutMapping(path = "/v1/movie/{movieId}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public int updateMovie(@PathVariable int movieId, @RequestBody Movie movie) {
        logger.info("Update movie with id: {}", movieId);

        movie.setId(movieId);

        return movieService.updateMovie(movie);
    }

    @ProtectedBy({UserRole.ADMIN})
    @PostMapping(path = "/v1/movie", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public int insertMovie(@RequestBody Movie movie) {
        int newMovieId = movieService.insertMovie(movie);

        logger.info("Created new movie with id: {}", newMovieId);

        return newMovieId;
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

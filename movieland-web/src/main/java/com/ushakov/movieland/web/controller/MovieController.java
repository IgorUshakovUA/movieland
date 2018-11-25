package com.ushakov.movieland.web.controller;

import com.ushakov.movieland.common.*;
import com.ushakov.movieland.entity.Movie;
import com.ushakov.movieland.entity.MovieDetailed;
import com.ushakov.movieland.service.MovieService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RestController
public class MovieController {
    private MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @RequestMapping(path = "/v1/movie", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Movie> getAll(@RequestParam(name = "rating", required = false) SortType ratingOrder, @RequestParam(name = "price", required = false) SortType priceOrder) {
        if (ratingOrder == null && priceOrder == null) {
            return movieService.getAll(null);
        }

        return movieService.getAll(getRequestSearchParam(ratingOrder, priceOrder));
    }

    @RequestMapping(path = "/v1/movie/{movieId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public MovieDetailed getMovieById(@PathVariable int movieId) {
        return movieService.getMovieById(movieId);
    }

    @RequestMapping(path = "/v1/movie/random", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Movie> getThreeRandomMovies() {
        return movieService.getThreeRandomMovies();
    }

    @RequestMapping(path = "/v1/movie/genre/{genreId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Movie> getMoviesByGenre(@PathVariable int genreId, @RequestParam(name = "rating", required = false) SortType ratingOrder, @RequestParam(name = "price", required = false) SortType priceOrder) {
        if (ratingOrder == null && priceOrder == null) {
            return movieService.getMoviesByGenre(genreId, null);
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

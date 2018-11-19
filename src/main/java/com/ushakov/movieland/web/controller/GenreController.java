package com.ushakov.movieland.web.controller;

import com.ushakov.movieland.entity.Genre;
import com.ushakov.movieland.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GenreController {
    private GenreService genreService;

    @Autowired
    public GenreController(@Qualifier("cachedGenreService") GenreService genreService) {
        this.genreService = genreService;
    }

    @RequestMapping(path = "/v1/genre", method = RequestMethod.GET)
    public List<Genre> getAll() {
        return genreService.getAll();
    }

    public void setGenreService(GenreService genreService) {
        this.genreService = genreService;
    }
}

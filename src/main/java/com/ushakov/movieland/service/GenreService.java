package com.ushakov.movieland.service;

import com.ushakov.movieland.entity.Genre;

import java.util.List;

public interface GenreService {
    List<Genre> getAll();
    Genre getGenreById(int id);
}

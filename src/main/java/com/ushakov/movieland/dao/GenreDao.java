package com.ushakov.movieland.dao;

import com.ushakov.movieland.entity.Genre;

import java.util.List;

public interface GenreDao {
    List<Genre> getAll();
    Genre getGenreById(int id);
}

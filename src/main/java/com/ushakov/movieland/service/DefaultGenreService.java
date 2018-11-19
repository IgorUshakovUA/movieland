package com.ushakov.movieland.service;

import com.ushakov.movieland.dao.GenreDao;
import com.ushakov.movieland.entity.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultGenreService implements GenreService {
    private GenreDao genreDao;

    @Autowired
    public DefaultGenreService(@Qualifier("cachedGenreDao") GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    @Override
    public List<Genre> getAll() {
        return genreDao.getAll();
    }

    @Override
    public Genre getGenreById(int id) {
        return genreDao.getGenreById(id);
    }
}

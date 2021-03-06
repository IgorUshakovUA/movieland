package com.ushakov.movieland.dao.cache;

import com.ushakov.movieland.dao.GenreDao;
import com.ushakov.movieland.entity.Genre;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Primary
@Repository
public class CachedGenreDao implements GenreDao {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private GenreDao genreDao;
    private volatile Map<Integer, Genre> cache = new HashMap<>();
    private volatile Map<Integer, List<Genre>> cacheByMovieId = new ConcurrentHashMap<>();

    @Autowired
    public CachedGenreDao(@Qualifier("jdbcGenreDao") GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    @Override
    public List<Genre> getAll() {
        List<Genre> genreList = new ArrayList<>(cache.values());

        logger.debug("Genres from cache, size: {}", genreList.size());
        logger.trace("Genres: {}", genreList);

        return genreList;
    }

    @Override
    public List<Genre> getGenresByMovieId(int movieId) {
        List<Genre> genreList = cacheByMovieId.get(movieId);

        if (genreList != null) {
            return genreList;
        }

        genreList = genreDao.getGenresByMovieId(movieId);

        cacheByMovieId.putIfAbsent(movieId, genreList);

        return genreList;
    }

    @Scheduled(fixedRateString = "${genre.cache.refresh.millis}", initialDelayString = "${genre.cache.refresh.millis}")
    @PostConstruct
    public void reloadCache() {
        List<Genre> genreList = genreDao.getAll();
        Map<Integer, Genre> newCache = new HashMap<>();

        for (Genre genre : genreList) {
            newCache.put(genre.getId(), genre);
        }

        logger.debug("Reload genres from db, size: {}", genreList.size());
        logger.trace("Genres: {}", genreList);

        cache = newCache;

        logger.debug("Clear cache of genres by genre group id.");

        cacheByMovieId = new ConcurrentHashMap<>();
    }
}
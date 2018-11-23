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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Primary
@Repository
public class CachedGenreDao implements GenreDao {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private GenreDao genreDao;
    private volatile Map<Integer, Genre> cache = new HashMap<>();

    @Autowired
    public CachedGenreDao(@Qualifier("jdbcGenreDao") GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    @Override
    public List<Genre> getAll() {
        List<Genre> genreList = new ArrayList<>();
        for (Genre value : cache.values()) {
            genreList.add((Genre) value.clone());
        }

        logger.debug("Genres from cache, size: {}", genreList.size());
        logger.trace("Genres: {}", genreList);

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
    }
}
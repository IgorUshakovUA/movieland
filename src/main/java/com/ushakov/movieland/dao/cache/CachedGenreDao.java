package com.ushakov.movieland.dao.cache;

import com.ushakov.movieland.dao.GenreDao;
import com.ushakov.movieland.entity.Genre;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class CachedGenreDao implements GenreDao {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private GenreDao genreDao;
    private volatile boolean allInCache;
    private Map<Integer, Genre> cache = new ConcurrentHashMap<>();

    @Autowired
    public CachedGenreDao(GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    @Override
    public List<Genre> getAll() {
        List<Genre> genreList;
        if (!allInCache) {
            genreList = genreDao.getAll();
            synchronized (cache) {
                for (Genre genre : genreList) {
                    cache.put(genre.getId(), genre);
                    allInCache = true;
                }
            }

            logger.debug("Genres from db: {}", genreList);

            return genreList;
        }
        synchronized (cache) {
            genreList = new ArrayList<>(cache.values());

            logger.debug("Genres from cache: {}", genreList);

            return genreList;
        }
    }

    @Override
    public Genre getGenreById(int id) {
        Genre result = cache.get(id);
        if (result != null) {
            logger.debug("Genre from cache: {}", result);

            return result;
        } else {
            result = genreDao.getGenreById(id);
            cache.put(id, result);

            logger.debug("Genre from db: {}", result);

            return result;
        }
    }

    @Scheduled(fixedRate = 1440000)
    public void clearCache() {
        synchronized (cache) {
            cache.clear();
            allInCache = false;
        }
    }
}

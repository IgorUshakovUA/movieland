package com.ushakov.movieland.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class DefaultCacheManager implements CacheManager {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private Map<String, Cache> cacheMap = new HashMap<>();

    @Override
    public Cache getCache(String s) {
        return cacheMap.get(s);
    }

    @Override
    public Collection<String> getCacheNames() {
        return cacheMap.keySet();
    }

    @Autowired
    public void setCaches(java.util.Collection<? extends Cache> caches) {
        for (Cache cache : caches) {
            cacheMap.put(cache.getName(), cache);
        }

        logger.debug("{} caches were added to the cache manager.", caches.size());
    }
}

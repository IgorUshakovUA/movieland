package com.ushakov.movieland.cache;

import org.junit.Test;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class DefaultCacheManagerTest {

    @Test
    public void testGetCache() {
        // Prepare
        Set<Cache> caches = new HashSet<>();
        Cache cache = new DefaultCache("test");
        caches.add(cache);

        DefaultCacheManager cacheManager = new DefaultCacheManager();
        cacheManager.setCaches(caches);

        // Test
        assertEquals(cache, cacheManager.getCache("test"));
    }

    @Test
    public void testGetNames() {
        // Prepare
        Set<Cache> caches = new HashSet<>();
        Cache cache = new DefaultCache("test");
        caches.add(cache);

        DefaultCacheManager cacheManager = new DefaultCacheManager();
        cacheManager.setCaches(caches);

        // Test
        Collection<String> names = cacheManager.getCacheNames();
        assertEquals(1, names.size());
        assertTrue(names.contains("test"));
    }

}
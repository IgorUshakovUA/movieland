package com.ushakov.movieland.cache;

import com.ushakov.movieland.entity.Genre;
import org.junit.Test;
import org.springframework.cache.Cache;

import java.util.Map;
import java.util.concurrent.Callable;

import static org.junit.Assert.*;

public class DefaultCacheTest {

    @Test
    public void testGetNativeCache() {
        // Prepare
        Cache cache = new DefaultCache("test");

        // Test
        assertTrue(cache.getNativeCache() instanceof Map);
        assertEquals(0, ((Map) (cache.getNativeCache())).size());

    }

    @Test
    public void testGetValueWrapper() {
        // Prepare
        Cache cache = new DefaultCache("test");
        cache.put("test", "test_value");

        // Test
        Cache.ValueWrapper valueWrapper = cache.get("test");

        assertEquals("test_value", valueWrapper.get());
    }

    @Test
    public void testGetGenre() {
        // Prepare
        Genre expectedGenre = new Genre();
        expectedGenre.setId(1);
        expectedGenre.setName("genre");

        Cache cache = new DefaultCache("genres");
        cache.put("genre", expectedGenre);

        // Test
        Genre actualGenre = cache.get("genre", Genre.class);

        assertEquals(expectedGenre, actualGenre);
    }

    @Test
    public void testGetGenreFromCallable() {
        // Prepare
        Genre expectedGenre = new Genre();
        expectedGenre.setId(1);
        expectedGenre.setName("genre");

        Callable<Genre> callable = () -> {
            return expectedGenre;
        };

        Cache cache = new DefaultCache("genres");

        // Test
        Genre actualGenre = cache.get("unknown", callable);

        assertEquals(expectedGenre, actualGenre);
    }

    @Test
    public void testGetGenreNotFromCallable() {
        // Prepare
        Genre expectedGenre = new Genre();
        expectedGenre.setId(1);
        expectedGenre.setName("genre");

        Genre callableGenre = new Genre();
        callableGenre.setId(2);
        callableGenre.setName("genre2");

        Callable<Genre> callable = () -> {
            return callableGenre;
        };

        Cache cache = new DefaultCache("genres");
        cache.put("genre", expectedGenre);

        // Test
        Genre actualGenre = cache.get("genre", callable);

        assertEquals(expectedGenre, actualGenre);
        assertNotEquals(callableGenre, actualGenre);
    }

    @Test
    public void testPut() {
        // Prepare
        Genre expectedGenre = new Genre();
        expectedGenre.setId(1);
        expectedGenre.setName("genre");

        Cache cache = new DefaultCache("genres");

        // Test
        assertEquals(null, cache.get("genre", Genre.class));

        cache.put("genre", expectedGenre);
        Genre actualGenre = cache.get("genre", Genre.class);

        assertEquals(expectedGenre, actualGenre);
    }

    @Test
    public void testEvict() {
        // Prepare
        Genre expectedGenre = new Genre();
        expectedGenre.setId(1);
        expectedGenre.setName("genre");

        Cache cache = new DefaultCache("genres");
        cache.put("genre", expectedGenre);

        // Test
        Genre actualGenre = cache.get("genre", Genre.class);

        assertEquals(expectedGenre, actualGenre);

        cache.evict("genre");

        actualGenre = cache.get("genre", Genre.class);

        assertNull(actualGenre);
    }

    @Test
    public void testClear() {
        // Prepare
        Genre expectedGenre = new Genre();
        expectedGenre.setId(1);
        expectedGenre.setName("genre");

        Cache cache = new DefaultCache("genres");
        cache.put("genre", expectedGenre);

        // Test
        Genre actualGenre = cache.get("genre", Genre.class);

        assertEquals(expectedGenre, actualGenre);

        cache.clear();

        actualGenre = cache.get("genre", Genre.class);

        assertNull(actualGenre);
    }

    @Test
    public void testPutIfAbsent() {
        // Prepare
        Genre expectedGenre = new Genre();
        expectedGenre.setId(1);
        expectedGenre.setName("genre");

        Cache cache = new DefaultCache("genres");

        // Test
        Cache.ValueWrapper valueWrapper = cache.putIfAbsent("genres", expectedGenre);

        assertNull(valueWrapper);

        valueWrapper = cache.putIfAbsent("genres", expectedGenre);

        assertEquals(expectedGenre, valueWrapper.get());
    }
}
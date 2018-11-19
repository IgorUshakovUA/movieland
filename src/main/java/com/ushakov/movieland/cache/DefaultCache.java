package com.ushakov.movieland.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

public class DefaultCache implements Cache {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private String name;
    private Map<Object, Object> cache = Collections.synchronizedMap(new HashMap<>());
    private int liveTimeSeconds = 60;

    @Autowired
    public DefaultCache(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Object getNativeCache() {
        logger.trace("NativeCache: {}", cache);

        return cache;
    }

    @Override
    public ValueWrapper get(Object o) {
        Object value = cache.get(o);

        logger.debug("Result value: {}", value);

        return () -> {
            return value;
        };
    }

    @Override
    public <T> T get(Object o, Class<T> aClass) {
        T value = (T) cache.get(o);

        logger.debug("Result value: {}", value);

        return value;
    }

    @Override
    public <T> T get(Object o, Callable<T> callable) {
        T value = (T) cache.get(o);
        if (value == null) {
            try {
                value = callable.call();

                logger.debug("A callable was executed.");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        logger.debug("Result value: {}", value);

        return value;
    }

    @Override
    public void put(Object o, Object o1) {
        cache.put(o, o1);

        logger.debug("The pair was added: <{},{}>", o, o1);
    }

    @Override
    public ValueWrapper putIfAbsent(Object o, Object o1) {
        Object result;
        if ((result = cache.get(o)) == null) {
            cache.put(o, o1);

            logger.debug("The new pair was added: <{},{}>", o, o1);

            return null;
        }

        logger.debug("The pair exist: <{},{}>", o, o1);

        return () -> {
            return result;
        };
    }

    @Override
    public void evict(Object o) {
        cache.remove(o);

        logger.debug("Removed object: {}", o);
    }

    @Override
    public void clear() {
        cache.clear();

        logger.debug("The cache is empty now.");
    }

    @Autowired
    public void setLiveTimeSeconds(int liveTimeSeconds) {
        this.liveTimeSeconds = liveTimeSeconds;
    }
}

package com.ushakov.movieland.callable;

import java.util.concurrent.Callable;

public class EnrichCallable<T> implements Callable<T> {
    private EnrichCall<T> enrichCall;
    private int movieId;

    public EnrichCallable(int movieId, EnrichCall<T> enrichCall) {
        this.movieId = movieId;
        this.enrichCall = enrichCall;
    }

    @Override
    public T call() {
        return enrichCall.call(movieId);
    }
}

package com.ushakov.movieland.futuretask;

import java.util.concurrent.FutureTask;

public class EnrichFutureTask<V> extends FutureTask<V> {
    private int movieId;

    public EnrichFutureTask(int movieId, EnrichCall<V> enrichCall) {
        super(() -> enrichCall.call(movieId));

        this.movieId = movieId;
    }
}

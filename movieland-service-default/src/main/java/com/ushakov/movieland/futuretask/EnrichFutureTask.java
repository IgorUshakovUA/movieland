package com.ushakov.movieland.futuretask;

import java.util.concurrent.FutureTask;

public class EnrichFutureTask<V> extends FutureTask<V> {

    public EnrichFutureTask(int movieId, EnrichCall<V> enrichCall) {
        super(() -> enrichCall.call(movieId));
    }
}

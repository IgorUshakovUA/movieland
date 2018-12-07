package com.ushakov.movieland.futuretask;

public interface EnrichCall<V> {
    V call(int movieId);
}

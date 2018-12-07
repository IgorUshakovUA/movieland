package com.ushakov.movieland.callable;

public interface EnrichCall<V> {
    V call(int movieId);
}

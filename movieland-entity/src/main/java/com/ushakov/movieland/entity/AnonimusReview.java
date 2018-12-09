package com.ushakov.movieland.entity;

import java.util.Objects;

public class AnonimusReview {
    private int movieId;
    private String text;

    public AnonimusReview() {
        super();
    }

    public AnonimusReview(int movieId, String text) {
        this.movieId = movieId;
        this.text = text;
    }

    public int getMovieId() {
        return movieId;
    }

    public String getText() {
        return text;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "AnonimusReview{" +
                "movieId=" + movieId +
                ", text='" + text + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnonimusReview that = (AnonimusReview) o;
        return movieId == that.movieId &&
                Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movieId, text);
    }
}

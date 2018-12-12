package com.ushakov.movieland.common;

import com.ushakov.movieland.entity.User;

import java.util.Objects;

public class ReviewRequest {
    private int movieId;
    private String text;
    private User user;

    public ReviewRequest() {
        super();
    }

    public ReviewRequest(int movieId, String text) {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "ReviewRequest{" +
                "movieId=" + movieId +
                ", text='" + text + '\'' +
                ", user=" + user +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReviewRequest that = (ReviewRequest) o;
        return movieId == that.movieId &&
                Objects.equals(text, that.text) &&
                Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movieId, text, user);
    }
}

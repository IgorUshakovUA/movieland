package com.ushakov.movieland.entity;

import java.util.Objects;

public class Review implements Entity {
    private int id;
    private User user;
    private String text;

    public Review(int id, User user, String text) {
        this.id = id;
        this.user = user;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", user=" + user +
                ", text='" + text + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Review review = (Review) object;
        return id == review.id &&
                Objects.equals(user, review.user) &&
                Objects.equals(text, review.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, text);
    }
}

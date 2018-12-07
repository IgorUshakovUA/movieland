package com.ushakov.movieland.entity;

import java.util.Objects;

public class Movie implements Entity {
    private int id;
    private String nameRussian;
    private String nameNative;
    private int yearOfRelease;
    private double rating;
    private double price;
    private String picturePath;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameRussian() {
        return nameRussian;
    }

    public void setNameRussian(String nameRussian) {
        this.nameRussian = nameRussian;
    }

    public String getNameNative() {
        return nameNative;
    }

    public void setNameNative(String nameNative) {
        this.nameNative = nameNative;
    }

    public int getYearOfRelease() {
        return yearOfRelease;
    }

    public void setYearOfRelease(int yearOfRelease) {
        this.yearOfRelease = yearOfRelease;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", nameRussian='" + nameRussian + '\'' +
                ", nameNative='" + nameNative + '\'' +
                ", yearOfRelease=" + yearOfRelease +
                ", rating=" + rating +
                ", price=" + price +
                ", picturePath='" + picturePath + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }

        Movie movie = (Movie) object;

        if (movie == this) {
            return true;
        }

        return this.id == movie.id && this.nameNative != null && this.nameNative.equals(movie.nameNative) &&
                this.nameRussian != null && this.nameRussian.equals(movie.nameRussian) &&
                this.yearOfRelease == movie.yearOfRelease && (this.rating - movie.rating) < 1e-3 &&
                (this.price - movie.price) < 1e-3 && this.picturePath != null && this.picturePath.equals(movie.picturePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nameRussian, nameNative, yearOfRelease, rating, price, picturePath);
    }
}

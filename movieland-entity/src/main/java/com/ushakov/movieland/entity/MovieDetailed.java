package com.ushakov.movieland.entity;

import java.util.List;
import java.util.Objects;

public class MovieDetailed implements Entity {
    private int id;
    private String nameRussian;
    private String nameNative;
    private int yearOfRelease;
    private String descritpion;
    private double rating;
    private double price;
    private String picturePath;
    private List<Country> countries;
    private List<Genre> genres;
    private List<Review> reviews;

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

    public String getDescritpion() {
        return descritpion;
    }

    public void setDescritpion(String descritpion) {
        this.descritpion = descritpion;
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

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    @Override
    public String toString() {
        return "MovieDetailed{" +
                "id=" + id +
                ", nameRussian='" + nameRussian + '\'' +
                ", nameNative='" + nameNative + '\'' +
                ", yearOfRelease=" + yearOfRelease +
                ", descritpion='" + descritpion + '\'' +
                ", rating=" + rating +
                ", price=" + price +
                ", picturePath='" + picturePath + '\'' +
                ", countries=" + countries +
                ", genres=" + genres +
                ", reviews=" + reviews +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        MovieDetailed that = (MovieDetailed) object;
        return id == that.id &&
                yearOfRelease == that.yearOfRelease &&
                Double.compare(that.rating, rating) == 0 &&
                Double.compare(that.price, price) == 0 &&
                Objects.equals(nameRussian, that.nameRussian) &&
                Objects.equals(nameNative, that.nameNative) &&
                Objects.equals(descritpion, that.descritpion) &&
                Objects.equals(picturePath, that.picturePath) &&
                Objects.equals(countries, that.countries) &&
                Objects.equals(genres, that.genres) &&
                Objects.equals(reviews, that.reviews);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nameRussian, nameNative, yearOfRelease, descritpion, rating, price, picturePath, countries, genres, reviews);
    }
}

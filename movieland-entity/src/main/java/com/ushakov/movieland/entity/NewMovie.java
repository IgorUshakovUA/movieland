package com.ushakov.movieland.entity;

import java.util.List;
import java.util.Objects;

public class NewMovie {
    private int id;
    private String nameRussian;
    private String nameNative;
    private int yearOfRelease;
    private double price;
    private String picturePath;
    private List<Integer> countries;
    private List<Integer> genres;
    private String description;

    public NewMovie() {}

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

    public List<Integer> getCountries() {
        return countries;
    }

    public void setCountries(List<Integer> countries) {
        this.countries = countries;
    }

    public List<Integer> getGenres() {
        return genres;
    }

    public void setGenres(List<Integer> genres) {
        this.genres = genres;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "NewMovie{" +
                "id=" + id +
                ", nameRussian='" + nameRussian + '\'' +
                ", nameNative='" + nameNative + '\'' +
                ", yearOfRelease=" + yearOfRelease +
                ", price=" + price +
                ", picturePath='" + picturePath + '\'' +
                ", countries=" + countries +
                ", genres=" + genres +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewMovie newMovie = (NewMovie) o;
        return id == newMovie.id &&
                yearOfRelease == newMovie.yearOfRelease &&
                Double.compare(newMovie.price, price) == 0 &&
                Objects.equals(nameRussian, newMovie.nameRussian) &&
                Objects.equals(nameNative, newMovie.nameNative) &&
                Objects.equals(picturePath, newMovie.picturePath) &&
                Objects.equals(countries, newMovie.countries) &&
                Objects.equals(genres, newMovie.genres) &&
                Objects.equals(description, newMovie.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nameRussian, nameNative, yearOfRelease, price, picturePath, countries, genres, description);
    }
}

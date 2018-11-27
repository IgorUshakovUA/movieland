package com.ushakov.movieland.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NbuCurrency {
    private double rate;
    @JsonProperty("cc")
    private String name;

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "NbuCurrency{" +
                "rate=" + rate +
                ", name='" + name + '\'' +
                '}';
    }
}

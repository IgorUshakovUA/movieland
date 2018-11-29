package com.ushakov.movieland.common;

public enum Currency {
    EUR("EUR"),
    USD("USD");

    private String value;

    Currency(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}

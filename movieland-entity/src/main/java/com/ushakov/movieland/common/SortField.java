package com.ushakov.movieland.common;

public enum SortField {
    RATING("rating"),
    PRICE("price");

    private String value;

    SortField(String value) {
        this.value = value.trim().toLowerCase();
    }

    public String value() {
        return value;
    }
}

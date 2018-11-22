package com.ushakov.movieland.common;

public enum SortType {
    ASC("ASC"),
    DESC("DESC");

    private String value;

    SortType(String value) {
        this.value = value.toUpperCase();
    }

    public String value() {
        return value;
    }
}

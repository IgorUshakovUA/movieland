package com.ushakov.movieland.dao;

public enum SortType {
    ASC("ASC"),
    DESC("DESC");

    private String value;

    SortType(String value) {
        this.value = value.trim().toUpperCase();
    }

    public String value() {
        return  value;
    }
}

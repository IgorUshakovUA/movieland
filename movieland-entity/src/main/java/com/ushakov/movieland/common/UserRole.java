package com.ushakov.movieland.common;

public enum UserRole {
    ADMIN("ADMIN"),
    USER("USER");

    private String value;

    UserRole(String value) {
        this.value = value;
    }
}

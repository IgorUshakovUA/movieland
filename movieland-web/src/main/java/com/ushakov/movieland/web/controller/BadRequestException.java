package com.ushakov.movieland.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {
    public BadRequestException() {
        super();
    }

    private String message;

    public BadRequestException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

}

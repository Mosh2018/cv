package com.netum.cv.backend.exceptions;

import org.springframework.http.HttpStatus;

public class UserNotValidException extends RuntimeException {

    public HttpStatus status;
    public String message;
    public UserNotValidException(HttpStatus status, String message) {
        super(message);
        this.message = message;
        this.status = status;
    }
}

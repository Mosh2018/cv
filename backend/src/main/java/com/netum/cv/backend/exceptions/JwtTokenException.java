package com.netum.cv.backend.exceptions;

import org.springframework.http.HttpStatus;

public class JwtTokenException extends RuntimeException {

    public HttpStatus status;
    public String message;
    public JwtTokenException(HttpStatus status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }
}

package com.netum.cv.backend.exceptions;

import com.netum.cv.backend.modal.CustomStatus;

public class JwtTokenException extends RuntimeException {

    public CustomStatus status;
    public JwtTokenException(CustomStatus status) {
        super(status.getName());
        this.status = status;
    }
}

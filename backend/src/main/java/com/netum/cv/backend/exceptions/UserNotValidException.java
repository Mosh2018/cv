package com.netum.cv.backend.exceptions;

import com.netum.cv.backend.modal.CustomStatus;

public class UserNotValidException extends RuntimeException {

    public CustomStatus status;
    public UserNotValidException(CustomStatus status) {
        super(status.getName());
        this.status = status;
    }
}

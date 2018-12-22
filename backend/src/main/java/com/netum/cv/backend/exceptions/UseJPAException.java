package com.netum.cv.backend.exceptions;

import com.netum.cv.backend.modal.CustomStatus;

public class UseJPAException extends RuntimeException {

    public CustomStatus status;
    public UseJPAException(CustomStatus status) {
        super(status.getName());
        this.status = status;
    }
}

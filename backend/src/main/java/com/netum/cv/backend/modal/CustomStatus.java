package com.netum.cv.backend.modal;

import lombok.Getter;

@Getter
public enum CustomStatus {
    NOT_FOUND(""),
    IT_IS_OK(""),
    NOT_VALID(""),
    PASS_VALIDATION(""),
    SHORT_STRING("This value is too short valid string more than 1 character"),
    IT_IS_NOT_UNIQUE("The value it is not unique change the value"),
    LONG_STRING("This value is so long valid string less than 20 characters");

    private final String name;

    CustomStatus(String name) {
        this.name = name;
    }

}

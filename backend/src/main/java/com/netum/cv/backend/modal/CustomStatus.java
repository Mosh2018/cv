package com.netum.cv.backend.modal;

import lombok.Getter;

@Getter
public enum CustomStatus {
    NOT_FOUND("User not found, can not find user for this username"),
    IT_IS_OK(""),
    NOT_VALID(""),
    NOT_VALID_NO("phone number not valid"),
    PASS_VALIDATION(""),
    SHORT_STRING("This value is too short valid string more than 1 character"),
    IT_IS_NOT_UNIQUE("The value it is not unique change the value"),
    LONG_STRING("This value is so long valid string less than 20 characters"),
    EMPTY("Value can not be empty"),
    IT_IS_WEAK("Can not validate it"),
    BAD_EMAIL("This Email address is not valid"),
    BAD_JWT("invalid token use app exception"),
    BAD_SIGNATURE("Invalid JWT signature"),
    JWT_INVALID("Invalid JWT token"),
    UNSUPPORTED_JWT("Unsupported JWT token"),
    EMPTY_JWT("JWT claims string is empty."),
    JWT_TIME_EXPIRED("Expired JWT token"),
    USER_NOT_SAVED("Something goes wrong, user didn't saved"),
    INVALID_DATE("Date format invalid"),
    PROFILE_NOT_SAVED("Something goes wrong, Profile didn't saved");

    public String name;

    CustomStatus(String name) {
        this.name = name;
    }

    public void setCustomStatusAMessage(String message) {
        this.name.concat(" - " + message);
    }

}

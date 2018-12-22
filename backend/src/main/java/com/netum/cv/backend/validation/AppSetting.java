package com.netum.cv.backend.validation;

public enum AppSetting {

    MAX_AGE_SECS( "3600"),
    username_min("6"),
    username_max("20"),
    password_min("4"),
    password_max("30"),
    email_min("8"),
    email_max("30"),
    first_name_min("2"),
    first_name_max("16"),
    last_name_min("0"),
    last_name_max("16"),

    SECRET("30"),
    SECRET_KEY(""),
    TOKEN_PREFIX("Bearer "),
    HEADER_STRING("Authorization"),
    EXPERT_TIME("1200000")
    ;


    public String text;
    AppSetting(String s) {
        this.text = s;
    }

    public int toInt() {
        return Integer.parseInt(text);
    }
    public Long toLong() {
        return Long.parseLong(text);
    }
    public void addString(String text) {
        this.text = text;
    }
}

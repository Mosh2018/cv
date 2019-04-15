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
    zipCode_min("2"),
    zipCode_max("10"),
    city_min("3"),
    city_max("20"),
    country_min("3"),
    country_max("20"),
    address_min("5"),
    address_max("30"),
    nationality_min("0"),
    nationality_max("20"),

    SECRET("30"),
    SECRET_KEY(""),
    TOKEN_PREFIX("Bearer "),
    HEADER_STRING("Authorization"),
    EXPERT_TIME("600000")
    ;


    public String text;
    AppSetting(String value) {
        this.text = value;
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

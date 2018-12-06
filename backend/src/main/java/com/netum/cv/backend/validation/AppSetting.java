package com.netum.cv.backend.validation;

public enum AppSetting {

    username_min("6"),
    username_max("20"),
    password_min("4"),
    password_max("30"),
    email_min("8"),
    email_max("30"),
    firstname_min("2"),
    firstname_max("16"),
    lastname_min("0"),
    lastname_max("16")
    ;


    public String s;
    AppSetting(String s) {
        this.s = s;
    }

    public int toInt() {
        return Integer.parseInt(s);
    }
}

package com.netum.cv.backend.service;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class DateTimeService {

    public Date getDate() {
        return new Date();
    }

    public Date addMillisToDateTime(Long millis) {
        return new Date(getDate().getTime() + millis);
    }
}

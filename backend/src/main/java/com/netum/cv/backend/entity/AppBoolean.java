package com.netum.cv.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppBoolean {
    private boolean isTrue;
    private String cause;

    public static AppBoolean build(boolean x, String y){
        return new AppBoolean(x, y);
    }
}

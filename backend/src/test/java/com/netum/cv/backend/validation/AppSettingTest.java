package com.netum.cv.backend.validation;

import org.junit.Test;

import static com.netum.cv.backend.validation.AppSetting.*;
import static org.junit.Assert.*;

public class AppSettingTest extends TestBaseForValidation {


    @Test
    public void testToIntMethod() {
        Object x = first_name_max.toInt();
        assertEquals("java.lang.Integer", x.getClass().getName());
    }

    @Test
    public void testTextMethod() {
        Object x = HEADER_STRING.text;
        assertEquals("java.lang.String", x.getClass().getName());
    }

    @Test
    public void testTheAddString() {
        SECRET_KEY.addString("test_String");
        assertEquals("test_String", SECRET_KEY.text);
    }
}
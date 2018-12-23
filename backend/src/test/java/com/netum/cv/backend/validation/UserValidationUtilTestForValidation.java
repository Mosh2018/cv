package com.netum.cv.backend.validation;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserValidationUtilTestForValidation extends TestBaseForValidation {

    @Test
    public void calculatePasswordStrength() {

        assertEquals(3, UserValidationUtil.calculatePasswordStrength(strPasswords[0]));
        assertEquals(6, UserValidationUtil.calculatePasswordStrength(strPasswords[1]));
        assertEquals(6, UserValidationUtil.calculatePasswordStrength(strPasswords[2]));
        assertEquals(8, UserValidationUtil.calculatePasswordStrength(strPasswords[3]));
        assertEquals(10, UserValidationUtil.calculatePasswordStrength(strPasswords[4]));
        assertEquals(0, UserValidationUtil.calculatePasswordStrength(strPasswords[5]));
        assertEquals(4, UserValidationUtil.calculatePasswordStrength(strPasswords[6]));
        assertEquals(3, UserValidationUtil.calculatePasswordStrength(strPasswords[7]));
        assertEquals(8, UserValidationUtil.calculatePasswordStrength(strPasswords[8]));
        assertEquals(6, UserValidationUtil.calculatePasswordStrength(strPasswords[9]));
        assertEquals(0, UserValidationUtil.calculatePasswordStrength(strPasswords[10]));
        assertEquals(10, UserValidationUtil.calculatePasswordStrength(strPasswords[11]));
        assertEquals(10, UserValidationUtil.calculatePasswordStrength(strPasswords[12]));
        assertEquals(8, UserValidationUtil.calculatePasswordStrength(strPasswords[13]));
        assertEquals(10, UserValidationUtil.calculatePasswordStrength(strPasswords[14]));
        assertEquals(10, UserValidationUtil.calculatePasswordStrength(strPasswords[15]));
        assertEquals(10, UserValidationUtil.calculatePasswordStrength(strPasswords[16]));

    }

    @Test
    public void invalidEmail() {
        assertFalse(UserValidationUtil.invalidEmail(emails[0]));
        assertTrue(UserValidationUtil.invalidEmail(emails[1]));
        assertTrue(UserValidationUtil.invalidEmail(emails[2]));
        assertFalse(UserValidationUtil.invalidEmail(emails[3]));
        assertTrue(UserValidationUtil.invalidEmail(emails[4]));
        assertTrue(UserValidationUtil.invalidEmail(emails[5]));
    }

    @Test
    public void isValidPhoneNumber() {
    assertFalse(UserValidationUtil.invalidatePhoneNumber(validPhoneNumbers[0]));
    assertFalse(UserValidationUtil.invalidatePhoneNumber(validPhoneNumbers[1]));
    assertFalse(UserValidationUtil.invalidatePhoneNumber(validPhoneNumbers[2]));

    }


    @Test
    public void invalidPhoneNumbers() {
        assertTrue(UserValidationUtil.invalidatePhoneNumber(invalidPhoneNumbers[0]));
        assertTrue(UserValidationUtil.invalidatePhoneNumber(invalidPhoneNumbers[1]));
        assertTrue(UserValidationUtil.invalidatePhoneNumber(invalidPhoneNumbers[2]));
        assertTrue(UserValidationUtil.invalidatePhoneNumber(invalidPhoneNumbers[3]));
        assertTrue(UserValidationUtil.invalidatePhoneNumber(invalidPhoneNumbers[4]));
        assertTrue(UserValidationUtil.invalidatePhoneNumber(invalidPhoneNumbers[5]));
    }

    @Test
    public void isValidBirthday() {
        assertFalse(UserValidationUtil.inValidBirthdayFormat(validBirthdays[0]));
    }

    @Test
    public void isInvalidBirthday() {
        assertTrue(UserValidationUtil.inValidBirthdayFormat(invalidBirthdays[0]));
    }
}
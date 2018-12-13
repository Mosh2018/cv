package com.netum.cv.backend.service;

import org.junit.Test;

import static org.junit.Assert.*;

public class JwtTokenServiceTest extends TestBase{

    @Test
    public void generateToken() {
    }

    @Test
    public void getUsernameFromJWT() {
    }

    @Test
    public void validateToken() {
    }

    @Test
    public void generateRandomSecretKey() {

        String secretKey = jwtTokenService.generateRandomSecretKey(20);
        String secretKey2= jwtTokenService.generateRandomSecretKey(20);
        assertTrue(secretKey.length() > 0);
        assertTrue(secretKey2.length() > 0);

        assertNotEquals(secretKey, secretKey2);

    }
}
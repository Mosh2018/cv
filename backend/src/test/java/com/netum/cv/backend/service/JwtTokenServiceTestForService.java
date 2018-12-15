package com.netum.cv.backend.service;

import org.junit.Test;

import static org.junit.Assert.*;

public class JwtTokenServiceTestForService extends TestBaseForService {

    @Test
    public void generateTokenTest() {
        String jwt = generateJwtForTest();
        assertFalse(jwt.isEmpty());
    }

    @Test
    public void getUsernameFromJWT() {
        String jwt = generateJwtForTest();
        assertEquals(username, jwtTokenService.getUsernameFromJWT(jwt));
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
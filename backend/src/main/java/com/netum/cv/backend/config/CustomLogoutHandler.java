package com.netum.cv.backend.config;

import com.netum.cv.backend.service.JwtTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CustomLogoutHandler extends SecurityContextLogoutHandler {

    @Autowired
    private JwtTokenService jwtTokenService;
    
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        jwtTokenService.changeSeurityKey();
    }
}

package com.netum.cv.backend.security;

import com.netum.cv.backend.exceptions.JwtTokenException;
import com.netum.cv.backend.modal.CustomStatus;
import com.netum.cv.backend.service.ApplicationUserDetailService;
import com.netum.cv.backend.service.JwtTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.netum.cv.backend.validation.AppSetting.HEADER_STRING;
import static com.netum.cv.backend.validation.AppSetting.TOKEN_PREFIX;


public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private ApplicationUserDetailService applicationUserDetailService;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String jwt = request.getHeader(HEADER_STRING.text);
        if(jwt == null || !jwt.startsWith(TOKEN_PREFIX.text)){
            filterChain.doFilter(request, response);
            return;
        }

        String token = jwt.replace(TOKEN_PREFIX.text, "");
        boolean invalid = jwtTokenService.isInvalidateToken(token);

        if (invalid){
            throw new JwtTokenException(CustomStatus.BAD_JWT);
        }

        String username = jwtTokenService.getJwtFromRequest(token);
        UserDetails userDetails = applicationUserDetailService.loadUserByUsername(username);

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        filterChain.doFilter(request, response);

    }
}


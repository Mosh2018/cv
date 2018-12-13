package com.netum.cv.backend.service;

import com.netum.cv.backend.modal.AppUser;
import com.netum.cv.backend.modal.LoginUser;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;

import static com.netum.cv.backend.validation.AppSetting.EXPERT_TIME;
import static com.netum.cv.backend.validation.AppSetting.SECRET;

@Service
public class JwtTokenService {

    private static final Logger logger;

    private static String SECRET_KEY = null;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    static {
        logger = LoggerFactory.getLogger(JwtTokenService.class);
    }


    public String generateToken(LoginUser loginUser){

        if (SECRET_KEY == null) {

            SECRET_KEY = passwordEncoder.encode(generateRandomSecretKey(SECRET.toInt()));
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUser.getUsername(),
                        loginUser.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        AppUser appUser = (AppUser) authentication.getPrincipal();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPERT_TIME.toLong());

        return  Jwts.builder()
                .claim("roles", appUser.getRoles())
                .setSubject(appUser.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();

    }

    public String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }


    public boolean isInvalidateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(authToken);
            return false;
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
        }
        return true;
    }


     String generateRandomSecretKey(int len) {

             char[] ch = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                     'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
                     'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
                     'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
                     'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
                     'w', 'x', 'y', 'z', '@', '$', '#', '!', ')', '(', '=', '?'};

             char[] c=new char[len];
             Random random=new Random();
             for (int i = 0; i < len; i++) {
                 c[i]=ch[random.nextInt(ch.length)];
             }

        return new String(c);
    }
}

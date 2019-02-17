package com.netum.cv.backend.service;

import com.netum.cv.backend.controller.AppController;
import com.netum.cv.backend.exceptions.JwtTokenException;
import com.netum.cv.backend.modal.AppUser;
import com.netum.cv.backend.modal.CustomStatus;
import com.netum.cv.backend.modal.LoginUser;
import io.jsonwebtoken.*;
import lombok.Setter;
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

import static com.netum.cv.backend.validation.AppSetting.*;

@Service
public class JwtTokenService extends AppController {

    private static final Logger logger;

    private static String SECRET_KEY = null;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private DateTimeService dateTimeService;

    static {
        logger = LoggerFactory.getLogger(JwtTokenService.class);
    }

    public void changeSeurityKey() {
        SECRET_KEY = passwordEncoder.encode(generateRandomSecretKey(SECRET.toInt()));
    }

    public String generateToken(LoginUser loginUser){

        if (SECRET_KEY == null) SECRET_KEY = passwordEncoder.encode(generateRandomSecretKey(SECRET.toInt()));
        System.out.println("MOSH#" + SECRET_KEY);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        AppUser appUser = (AppUser) authentication.getPrincipal();

        Date expiryDate = dateTimeService.addMillisToDateTime(EXPERT_TIME.toLong());
        // Todo add validation for user, secret key
        return  Jwts.builder()
                .claim("roles", appUser.getRoles())
                .setSubject(appUser.getUsername())
                .setIssuedAt(dateTimeService.getDate())
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

    public String getJwtFromRequest(String jwt) {
        String token = getTokenFromJwt(jwt);
        isInvalidateToken(token);
        return getUsernameFromJWT(token);
    }

    public String getTokenFromJwt(String jwt) {
        String token = jwt.replace(TOKEN_PREFIX.text, "");
        return token;
    }

    public void isInvalidateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(authToken);
        } catch (SignatureException ex) {
            throw new JwtTokenException(CustomStatus.BAD_SIGNATURE);
        } catch (MalformedJwtException ex) {
            throw new JwtTokenException(CustomStatus.JWT_INVALID);
        } catch (ExpiredJwtException ex) {
            throw new JwtTokenException(CustomStatus.JWT_TIME_EXPIRED);
        } catch (UnsupportedJwtException ex) {
            throw new JwtTokenException(CustomStatus.UNSUPPORTED_JWT);
        } catch (IllegalArgumentException ex) {
            throw new JwtTokenException(CustomStatus.EMPTY_JWT);
        } catch (RuntimeException e) {
            throw new JwtTokenException(CustomStatus.BAD_JWT);
        }
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

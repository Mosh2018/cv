package com.netum.cv.backend.service;

import com.netum.cv.backend.controller.AppController;
import com.netum.cv.backend.entity.User;
import com.netum.cv.backend.exceptions.JwtTokenException;
import com.netum.cv.backend.modal.AppUser;
import com.netum.cv.backend.modal.CustomStatus;
import com.netum.cv.backend.modal.LoginResponse;
import com.netum.cv.backend.modal.LoginUser;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class JwtTokenService extends AppController {

    private static String SECRET_KEY = null;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private DateTimeService dateTimeService;

    @Autowired
    private UserService userService;

    public void deleteSecurityKey() {
        userService.saveSecurityKey(null);
    }

    public String generateToken(LoginUser loginUser){
        AppUser appUser = getAppUser(loginUser);
        User user = userService.getUserEntity();
        SECRET_KEY = appUser.getSecurityKey();
        if(SECRET_KEY == null){
            generateAndSaveSecurityKeyToDB();
        }

        Date expiryDate = dateTimeService.addMillisToDateTime(EXPERT_TIME.toLong());
        // Todo add validation for user, secret key
        return  Jwts.builder()
                .claim("roles", appUser.getRoles())
                .claim("firstName", user.getFirstName())
                .claim("lastName", user.getLastName())
                .claim("email", user.getEmail())
                .setSubject(appUser.getUsername())
                .setIssuedAt(dateTimeService.getDate())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    private AppUser getAppUser(LoginUser loginUser) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return (AppUser) authentication.getPrincipal();
    }

    private void generateAndSaveSecurityKeyToDB() {
        SECRET_KEY = passwordEncoder.encode(generateRandomSecretKey(SECRET.toInt()));
        userService.saveSecurityKey(SECRET_KEY);
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

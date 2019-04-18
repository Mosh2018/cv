package com.netum.cv.backend.modal;

import com.netum.cv.backend.validation.UserValidationUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.OK;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private HttpStatus status;
    private String jwt;

    public static LoginResponse buildResponse(String jwt) {
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setJwt(jwt);
        if(UserValidationUtil.isEmptyString(jwt)){
           loginResponse.setStatus(FORBIDDEN);
        } else {
            loginResponse.setStatus(OK);
        }
        return loginResponse;
    }
}

package com.netum.cv.backend.modal;

import com.netum.cv.backend.validation.UserValidationUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.netum.cv.backend.modal.CustomStatus.EMPTY_JWT;
import static com.netum.cv.backend.modal.CustomStatus.IT_IS_OK;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private CustomStatus status;
    private String jwt;
    private String firstName;
    private String lastName;

    public static LoginResponse buildResponse(String jwt) {
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setJwt(jwt);
        if(UserValidationUtil.isEmptyString(jwt)){
           loginResponse.setStatus(EMPTY_JWT);
        } else {
            loginResponse.setStatus(IT_IS_OK);
        }
        return loginResponse;
    }
}

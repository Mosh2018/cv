package com.netum.cv.backend.validation;

import com.netum.cv.backend.exceptions.UserNotValidException;
import com.netum.cv.backend.modal.CustomResponse;
import com.netum.cv.backend.modal.RequestUser;
import com.netum.cv.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.netum.cv.backend.modal.CustomStatus.*;
import static com.netum.cv.backend.validation.AppSetting.*;
import static com.netum.cv.backend.validation.UserValidationUtil.*;

@Component
public class SignUpUserValidation {

    @Autowired
    private UserRepository userRepository;

    public CustomResponse validateSignUp(RequestUser user) {

        isValidUserName(user.getUsername());
        isValidPassword(user.getPassword());
        isValidEmail(user.getEmail());
        isValidStringValue(user.getFirstName(), first_name_min.toInt(), first_name_max.toInt());
        isValidStringValue(user.getLastName(), last_name_min.toInt(), last_name_max.toInt());

        return CustomResponse.build(PASS_VALIDATION);
    }

    private void isValidUserName(String username) {
        isValidStringValue(username, username_min.toInt(), username_max.toInt());
        boolean isUsed = userRepository.existsByUsername(username);
        if (isUsed) {
            throw new UserNotValidException(IT_IS_NOT_UNIQUE);
        }
    }

    private void isValidPassword(String password) {

        isValidStringValue(password, password_min.toInt(), password_max.toInt());
        if(calculatePasswordStrength(password) <= password_min.toInt()){
            throw new UserNotValidException(IT_IS_WEAK);
        }
    }

    private void isValidEmail(String email) {
        isValidStringValue(email, email_min.toInt(), email_max.toInt());
        if(invalidEmail(email)){
            throw new UserNotValidException(BAD_EMAIL);
        }
        boolean isUsed = userRepository.existsByEmail(email);
        if (isUsed) {
            throw new UserNotValidException(IT_IS_NOT_UNIQUE);
        }
    }

}

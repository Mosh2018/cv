package com.netum.cv.backend.validation;

import com.netum.cv.backend.entity.AppBoolean;
import com.netum.cv.backend.modal.RequestUser;
import com.netum.cv.backend.modal.ValidationResult;
import com.netum.cv.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.netum.cv.backend.validation.AppSetting.*;
import static com.netum.cv.backend.validation.UserValidationUtil.*;
import static org.springframework.http.HttpStatus.*;

@Component
public class SignUpUserValidation {

    @Autowired
    private UserRepository userRepository;

    public List<ValidationResult>  validateSignUp(RequestUser user) {
        List<ValidationResult> results = new ArrayList<>();
        isValidUserName(user.getUsername(), results);
        isValidPassword(user.getPassword(), results);
        isValidEmail(user.getEmail(), results);
        validateFirstAndLastName(user, results);
        return results;
    }

    private void validateFirstAndLastName(RequestUser user, List<ValidationResult> results) {
        AppBoolean validFirstName = isValidStringValue(user.getFirstName(), first_name_min.toInt(), first_name_max.toInt());
        AppBoolean validLastName = isValidStringValue(user.getLastName(), last_name_min.toInt(), last_name_max.toInt());

        if (validFirstName.isTrue() == false) {
            results.add(ValidationResult.build("first name " + validFirstName.getCause(), NO_CONTENT));
        }
        if (validLastName.isTrue() == false) {
            results.add(ValidationResult.build("last name " + validFirstName.getCause(), NO_CONTENT));
        }
    }

    private void isValidUserName(String username, List<ValidationResult> results) {
        AppBoolean valid = isValidStringValue(username, username_min.toInt(), username_max.toInt());
        if (valid.isTrue()) {
            boolean isUsed = userRepository.existsByUsername(username);
            if(isUsed) results.add(ValidationResult.build("username has already been taken", IM_USED));
        } else {
            results.add(ValidationResult.build("username " + valid.getCause(), NO_CONTENT));
        }
    }

    private void isValidPassword(String password, List<ValidationResult> results) {
        AppBoolean valid = isValidStringValue(password, password_min.toInt(), password_max.toInt());
        if (valid.isTrue()) {
            int s = calculatePasswordStrength(password);
            if(s <= password_min.toInt()) results.add(ValidationResult.build("password is too weak", SEE_OTHER));
        } else {
            results.add(ValidationResult.build("password " + valid.getCause(), NO_CONTENT));
        }
    }

    private void isValidEmail(String email, List<ValidationResult> results) {
        AppBoolean valid =isValidStringValue(email, email_min.toInt(), email_max.toInt());
        if(valid.isTrue() == false) {
            results.add(ValidationResult.build("email " + valid.getCause(), NO_CONTENT));
        } else if(invalidEmail(email)){
            results.add(ValidationResult.build("email is not valid", SEE_OTHER));
        } else {
            boolean isUsed = userRepository.existsByEmail(email);
            if (isUsed) results.add(ValidationResult.build("email has been used", IM_USED));
        }
    }
}

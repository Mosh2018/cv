package com.netum.cv.backend.validation;

import com.netum.cv.backend.exceptions.UserNotValidException;
import com.netum.cv.backend.modal.CustomResponse;
import com.netum.cv.backend.modal.RequestUser;
import com.netum.cv.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.netum.cv.backend.modal.CustomStatus.*;

@Component
public class UserValidation {

    @Autowired
    private UserRepository userRepository;

    public CustomResponse validateSignUp(RequestUser user) {
        isValidUserName(user.getUsername());



        // tarkistaa username laillisuutta
        // tarkistaa username pituuta
        // tarkistaa first name ei oo tyhjä ja se on 2 pitkä
        // tarkistaa tarkistaa salasana
        // tarkistaa email
        return CustomResponse.build(IT_IS_OK);
    }

    private void isValidUserName(String username) {
        if (username.length() < 2) {
            throw new UserNotValidException(SHORT_STRING);
        }
        if (username.length() > 20) {
            throw new UserNotValidException(LONG_STRING);
        }
        boolean isUsed = userRepository.existsByUsername(username);
        if (isUsed) {
            throw new UserNotValidException(IT_IS_NOT_UNIQUE);
        }
    }

}

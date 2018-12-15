package com.netum.cv.backend.validation;

import com.netum.cv.backend.exceptions.UserNotValidException;
import com.netum.cv.backend.modal.CustomStatus;
import com.netum.cv.backend.modal.RequestUser;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


public class SignUpUserValidationTestForValidation extends TestBaseForValidation {

    @Test
    public void throwExceptionsIfUsernameIsEmpty() {
        RequestUser requestUser = getRequestUser("",
                null, null, null, null);
        try {
            signUpUserValidation.validateSignUp(requestUser);
            fail();
        } catch (UserNotValidException e) {
            assertEquals(CustomStatus.EMPTY, e.status);
        }
    }

    @Test
    public void throwExceptionsIfUsernameLess6Characters() {
        RequestUser requestUser = getRequestUser("qwert",
                null, null, null, null);
        try {
            signUpUserValidation.validateSignUp(requestUser);
            fail();
        } catch (UserNotValidException e) {
            assertEquals(CustomStatus.SHORT_STRING, e.status);
        }
    }

    @Test
    public void throwExceptionIfUserNameOver20Characters(){
        RequestUser requestUser = getRequestUser("thisMostBeStringOverTwentyCharactersLong",
                null, null, null, null);
        try {
            signUpUserValidation.validateSignUp(requestUser);
            fail();
        } catch (UserNotValidException e) {
            assertEquals(CustomStatus.LONG_STRING, e.status);
        }
    }

    @Test
    public void throwExceptionIfUsernameExistInDataBase() {
        RequestUser requestUser = getRequestUser("Mohamid",
                null, null, null, null);
        when(userRepository.existsByUsername(anyString())).thenReturn(true);

        try {
            signUpUserValidation.validateSignUp(requestUser);
            fail();
        } catch (UserNotValidException e) {
            assertEquals(CustomStatus.IT_IS_NOT_UNIQUE, e.status);
        }
    }

    @Test
    public void throwExceptionsIfFirstNameLess2Characters() {
        RequestUser requestUser = getRequestUser(null,
                "w", null, null, null);
        try {
            signUpUserValidation.validateSignUp(requestUser);
            fail();
        } catch (UserNotValidException e) {
            assertEquals(CustomStatus.SHORT_STRING, e.status);
        }
    }

    @Test
    public void noExceptionsIfLastNameEmptyCharacters() {
        RequestUser requestUser = getRequestUser(null,
                null, "", null, null);
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        try {
            signUpUserValidation.validateSignUp(requestUser);

        } catch (UserNotValidException e) {
            fail();
            assertEquals(CustomStatus.EMPTY, e.status);
        }
    }

    @Test
    public void throwExceptionIfPasswordIsWeak() {
        RequestUser requestUser = getRequestUser(null,
                null, null, null, "123456");
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        try {
            signUpUserValidation.validateSignUp(requestUser);
            fail();
        } catch (UserNotValidException e) {
            assertEquals(CustomStatus.IT_IS_WEAK, e.status);
        }
    }

    @Test
    public void noExceptionIfPasswordIsNotWeak() {
        RequestUser requestUser = getRequestUser(null,
                null, null, null, strPasswords[2]);
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        try {
            signUpUserValidation.validateSignUp(requestUser);

        } catch (UserNotValidException e) {
            fail();
            assertEquals(CustomStatus.IT_IS_WEAK, e.status);
        }
    }

    @Test
    public void throwExceptionIfPasswordIsShort() {
        RequestUser requestUser = getRequestUser(null,
                null, null, null, strPasswords[5]);
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        try {
            signUpUserValidation.validateSignUp(requestUser);
            fail();
        } catch (UserNotValidException e) {

            assertEquals(CustomStatus.SHORT_STRING, e.status);
        }
    }

    @Test
    public void throwExceptionIfEmailLogicallyShort() {
        RequestUser requestUser = getRequestUser(null,
                null, null, emails[0],null);
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        try {
            signUpUserValidation.validateSignUp(requestUser);
            fail();
        } catch (UserNotValidException e) {
            assertEquals(CustomStatus.SHORT_STRING, e.status);
        }
    }

    @Test
    public void throwExceptionIfEmailNotValid() {
        RequestUser requestUser = getRequestUser(null,
                null, null, emails[1],null);
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        try {
            signUpUserValidation.validateSignUp(requestUser);
            fail();
        } catch (UserNotValidException e) {
            assertEquals(CustomStatus.BAD_EMAIL, e.status);
        }
    }

    @Test
    public void noExceptionIfEmailIsValid() {
        RequestUser requestUser = getRequestUser(null,
                null, null, emails[3],null);
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        try {
            signUpUserValidation.validateSignUp(requestUser);
        } catch (UserNotValidException e) {
            fail();
            assertEquals(CustomStatus.BAD_EMAIL, e.status);
        }
    }

}
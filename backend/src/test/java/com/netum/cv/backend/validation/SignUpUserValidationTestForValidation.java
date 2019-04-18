package com.netum.cv.backend.validation;

import com.netum.cv.backend.exceptions.UserNotValidException;
import com.netum.cv.backend.modal.RequestUser;
import com.netum.cv.backend.modal.ValidationResult;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import javax.xml.bind.ValidationEvent;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


public class SignUpUserValidationTestForValidation extends TestBaseForValidation {

    @Test
    public void returnNotEmptyValidationResultsIfUsernameIsEmpty() {
        RequestUser requestUser = getRequestUser("",
                null, null, null, null);
        List<ValidationResult> results = signUpUserValidation.validateSignUp(requestUser);
        assertEquals(1, results.size());
        assertEquals("username can't be empty", results.get(0).getMessage());
        assertEquals(HttpStatus.NO_CONTENT, results.get(0).getCustomStatus());

    }

    @Test
    public void returnValidationsResultWithOneValidationIfUsernameLess6Characters() {
        RequestUser requestUser = getRequestUser("qwert",
                null, null, null, null);
        List<ValidationResult> results = signUpUserValidation.validateSignUp(requestUser);
        assertEquals(1, results.size());
        assertEquals("username can't be shorter than 6 characters", results.get(0).getMessage());
        assertEquals(HttpStatus.NO_CONTENT, results.get(0).getCustomStatus());
    }

    @Test
    public void returnValidationsResultWithOneValidationIfUserNameOver20Characters(){

        RequestUser requestUser = getRequestUser("thisMostBeStringOverTwentyCharactersLong",
                null, null, null, null);
        List<ValidationResult> results = signUpUserValidation.validateSignUp(requestUser);
        assertEquals(1, results.size());
        assertEquals("username can't be more than 20 characters", results.get(0).getMessage());
        assertEquals(HttpStatus.NO_CONTENT, results.get(0).getCustomStatus());
    }

    @Test
    public void returnValidationsResultWithOneValidationIfUsernameExistInDataBase() {
        RequestUser requestUser = getRequestUser("Mohamid",
                null, null, null, null);
        when(userRepository.existsByUsername(anyString())).thenReturn(true);
        List<ValidationResult> results = signUpUserValidation.validateSignUp(requestUser);
        assertEquals(1, results.size());
        assertEquals("username has already been taken", results.get(0).getMessage());
        assertEquals(HttpStatus.IM_USED, results.get(0).getCustomStatus());
    }

    @Test
    public void returnValidationsResultWithOneValidationIfFirstNameLess2Characters() {
        RequestUser requestUser = getRequestUser(null,
                "w", null, null, null);
        List<ValidationResult> results = signUpUserValidation.validateSignUp(requestUser);
        assertEquals(1, results.size());
        assertEquals("first name can't be shorter than 2 characters", results.get(0).getMessage());
        assertEquals(HttpStatus.NO_CONTENT, results.get(0).getCustomStatus());
    }

    @Test
    public void returnEmptyValidationResultIfLastNameEmpty() {
        RequestUser requestUser = getRequestUser(null,
                null, "", null, null);
        List<ValidationResult> results = signUpUserValidation.validateSignUp(requestUser);
        assertEquals(0, results.size());
    }

    @Test
    public void returnValidationWithOneIfPasswordIsWeak() {
        RequestUser requestUser = getRequestUser(null,
                null, null, null, "123456");
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        List<ValidationResult> results = signUpUserValidation.validateSignUp(requestUser);
        assertEquals(1, results.size());
        assertEquals("password is too weak", results.get(0).getMessage());
        assertEquals(HttpStatus.SEE_OTHER, results.get(0).getCustomStatus());
    }

    @Test
    public void returnEmptyValidationResultIfPasswordIsNotWeak() {
        RequestUser requestUser = getRequestUser(null,
                null, null, null, strPasswords[2]);
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        List<ValidationResult> results = signUpUserValidation.validateSignUp(requestUser);
        assertEquals(0, results.size());
    }

    @Test
    public void returnValidationWithOneIfPasswordIsShort() {
        RequestUser requestUser = getRequestUser(null,
                null, null, null, strPasswords[5]);
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        List<ValidationResult> results = signUpUserValidation.validateSignUp(requestUser);
        assertEquals(1, results.size());
        assertEquals("password can't be shorter than 4 characters", results.get(0).getMessage());
        assertEquals(HttpStatus.NO_CONTENT, results.get(0).getCustomStatus());
    }

    @Test
    public void returnValidationWithOneIfEmailLogicallyShort() {
        RequestUser requestUser = getRequestUser(null,
                null, null, emails[0],null);
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        List<ValidationResult> results = signUpUserValidation.validateSignUp(requestUser);
        assertEquals(1, results.size());
        assertEquals("email can't be shorter than 8 characters", results.get(0).getMessage());
        assertEquals(HttpStatus.NO_CONTENT, results.get(0).getCustomStatus());
    }

    @Test
    public void returnValidationWithOneIfEmailNotValid() {
        RequestUser requestUser = getRequestUser(null,
                null, null, emails[1],null);
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        List<ValidationResult> results = signUpUserValidation.validateSignUp(requestUser);
        assertEquals(1, results.size());
        assertEquals("email is not valid", results.get(0).getMessage());
        assertEquals(HttpStatus.SEE_OTHER, results.get(0).getCustomStatus());
    }

    @Test
    public void returnEmptyValidationResultIfEmailIsValid() {
        RequestUser requestUser = getRequestUser(null,
                null, null, emails[3],null);
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        List<ValidationResult> results = signUpUserValidation.validateSignUp(requestUser);
        assertEquals(0, results.size());
    }
}

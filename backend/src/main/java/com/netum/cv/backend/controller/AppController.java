package com.netum.cv.backend.controller;

import com.netum.cv.backend.exceptions.JwtTokenException;
import com.netum.cv.backend.exceptions.UseJPAException;
import com.netum.cv.backend.exceptions.UserNotValidException;
import com.netum.cv.backend.modal.CustomResponse;
import com.netum.cv.backend.modal.ValidationResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

public class AppController {

    @ExceptionHandler
    public ResponseEntity<CustomResponse> appExceptionHandler(UserNotValidException exception) {
        return getCustomResponseResponseEntity(exception.message, exception.status);
    }

    @ExceptionHandler
    public ResponseEntity<CustomResponse> jwtExceptionHandler(JwtTokenException exception) {
        return getCustomResponseResponseEntity(exception.message, exception.status);
    }

    @ExceptionHandler
    public ResponseEntity<CustomResponse> userJpaExceptions(UseJPAException exception) {
        return getCustomResponseResponseEntity(exception.message, exception.status);
    }

    private ResponseEntity<CustomResponse> getCustomResponseResponseEntity(String message, HttpStatus status) {
        List<ValidationResult> resultList = new ArrayList<>();
        resultList.add(ValidationResult.build(message, status));
        return new ResponseEntity(CustomResponse.build(status, resultList), HttpStatus.FORBIDDEN);
    }
}

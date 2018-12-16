package com.netum.cv.backend.controller;

import com.netum.cv.backend.exceptions.JwtTokenException;
import com.netum.cv.backend.exceptions.UserNotValidException;
import com.netum.cv.backend.modal.CustomResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.netum.cv.backend.modal.CustomStatus.*;

public class AppController {

    public ResponseEntity<CustomResponse> getEntityResponseAnswer(CustomResponse customResponse) {

        if (customResponse.getCustomStatus().equals(PASS_VALIDATION)) {
            return new ResponseEntity(CustomResponse.build(IT_IS_OK), HttpStatus.OK);
        }
        return new ResponseEntity<>(customResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    public ResponseEntity<CustomResponse> appExceptionHandler(UserNotValidException exception) {

        switch (exception.status) {
            case IT_IS_NOT_UNIQUE:
                return new ResponseEntity<>(CustomResponse.build(exception.status), HttpStatus.IM_USED);
            case SHORT_STRING:
            case LONG_STRING:
                return new ResponseEntity<>(CustomResponse.build(exception.status), HttpStatus.LENGTH_REQUIRED);
            case EMPTY:
                return new ResponseEntity<>(CustomResponse.build(exception.status), HttpStatus.NO_CONTENT);
            case IT_IS_WEAK:
            case BAD_EMAIL:
                return  new ResponseEntity<>(CustomResponse.build(exception.status), HttpStatus.SEE_OTHER);
            case NOT_FOUND:
                return new ResponseEntity<>(CustomResponse.build(exception.status), HttpStatus.FORBIDDEN);
                default:
                    return getEntityResponseAnswer(CustomResponse.build(exception.status));
        }
    }

    @ExceptionHandler
    public ResponseEntity<CustomResponse> jwtExceptionHandler(JwtTokenException exception) {

        switch (exception.status) {
            case BAD_JWT:
            case BAD_SIGNATURE:
            case JWT_INVALID:
            case JWT_TIME_EXPIRED:
            case UNSUPPORTED_JWT:
            case EMPTY_JWT:
                return new ResponseEntity<>(CustomResponse.build(exception.status), HttpStatus.FORBIDDEN);

            default:
                return new ResponseEntity<>(CustomResponse.build(exception.status), HttpStatus.FORBIDDEN);
        }
    }
}

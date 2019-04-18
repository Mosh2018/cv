package com.netum.cv.backend.modal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomResponse {

    private Long timeStamp;
    private HttpStatus status;
    private List<ValidationResult> validationResult;

    public static CustomResponse build(HttpStatus httpStatus, List<ValidationResult> resultList) {
        CustomResponse status =  new CustomResponse();
        status.setStatus(httpStatus);
        status.setTimeStamp(System.currentTimeMillis());
        status.setValidationResult(resultList);
        return status;
    }
}

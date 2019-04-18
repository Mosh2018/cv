package com.netum.cv.backend.modal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ValidationResult {

    String message;
    HttpStatus customStatus;

    public static ValidationResult build(String m, HttpStatus r) {
        return new ValidationResult(m,r);

    }
}

package com.netum.cv.backend.modal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomResponse {

    private Long timeStamp;
    private String message;
    private CustomStatus status;

    public static CustomResponse build() {
        return new CustomResponse();
    }
    public static CustomResponse build(CustomStatus customStatus) {
        CustomResponse status =  new CustomResponse();
        status.setStatus(customStatus);
        status.setTimeStamp(System.currentTimeMillis());
        status.setMessage(customStatus.getName());
        return status;
    }
}

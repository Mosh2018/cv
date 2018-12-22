package com.netum.cv.backend.modal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CvProfile {

    private String phoneNumber;
    private String birthday;
    private String nationality;
    private String address;
    private String country;
    private String city;
    private String zipCode;
}

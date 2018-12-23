package com.netum.cv.backend.modal;

import com.netum.cv.backend.entity.Profile;
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

    public static CvProfile createCvProfile(Profile profile) {
        return new CvProfile(profile.getPhoneNumber(), profile.getBirthday(), profile.getNationality(), profile.getAddress(),
                profile.getCountry(), profile.getCity(), profile.getZipCode());
    }
}

package com.netum.cv.backend.entity;

import com.netum.cv.backend.modal.CvProfile;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.text.ParseException;

@Entity
@Table(name = "cv_profile")
@Getter
@Setter
@NoArgsConstructor
public class Profile extends ChangeTime{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "phone_number")
    private String phoneNumber;

    @NotBlank
    private String birthday;

    @NotBlank
    private String nationality;

    private String address;

    @NotBlank
    private String country;

    @NotBlank
    private String city;

    @NotBlank
    @Column(name = "zip_code")
    private String zipCode;

    public Profile(@NotBlank String phoneNumber,
                   @NotBlank String birthday,
                   @NotBlank String nationality,
                   String address,
                   @NotBlank String country,
                   @NotBlank String city,
                   @NotBlank String zipCode) {

        this.phoneNumber = phoneNumber;
        this.birthday = birthday;
        this.nationality = nationality;
        this.address = address;
        this.country = country;
        this.city = city;
        this.zipCode = zipCode;
    }

    public Profile initProfile(CvProfile cvProfile) throws ParseException {

        return new Profile(
                cvProfile.getPhoneNumber(),
                cvProfile.getBirthday(),
                cvProfile.getNationality(),
                cvProfile.getAddress(),
                cvProfile.getCountry(),
                cvProfile.getCity(),
                cvProfile.getZipCode());
    }
}

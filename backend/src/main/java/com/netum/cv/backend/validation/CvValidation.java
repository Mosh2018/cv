package com.netum.cv.backend.validation;

import com.netum.cv.backend.exceptions.UserNotValidException;
import com.netum.cv.backend.modal.CustomResponse;
import com.netum.cv.backend.modal.CustomStatus;
import com.netum.cv.backend.modal.CvProfile;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.netum.cv.backend.validation.AppSetting.*;
import static com.netum.cv.backend.validation.UserValidationUtil.*;

@Component
public class CvValidation {

    public CustomResponse validateProfile(CvProfile cvProfile) throws ParseException{

        isValidPhoneNumber(cvProfile.getPhoneNumber());
        isValidBirthday(cvProfile.getBirthday());
        isValidNationality(cvProfile.getNationality());
        isValidAddress(cvProfile.getAddress());
        isValidCity(cvProfile.getCity());
        isInvalidCountry(cvProfile.getCountry());
        isValidZipCode(cvProfile.getZipCode());
        return CustomResponse.build(CustomStatus.PASS_VALIDATION);
    }

    private void isInvalidCountry(String country) {
        isValidStringValue(country, country_min.toInt(), country_max.toInt());

    }

    private void isValidZipCode(String zipCode) {
        isValidStringValue(zipCode, zipCode_min.toInt(), zipCode_max.toInt());
    }

    private void isValidCity(String city) {
        isValidStringValue(city, city_min.toInt(), city_max.toInt());

    }

    private void isValidAddress(String address) {
        isValidStringValue(address, address_min.toInt(), address_max.toInt());
    }

    private void isValidNationality(String nationality) {
        isValidStringValue(nationality, nationality_min.toInt(), nationality_max.toInt());
    }

    private void isValidBirthday(String birthday) throws ParseException {
        if(inValidBirthdayFormat(birthday)) {
         throw new UserNotValidException(CustomStatus.INVALID_DATE);
        }
        Date now = getNowWithoutTime();
        Date userBirthday = new SimpleDateFormat("dd.MM.yyyy").parse(birthday);
        if (userBirthday.equals(now) || userBirthday.after(now)) {
            CustomStatus.INVALID_DATE.setCustomStatusAMessage("birthday can not be in future");
            throw new UserNotValidException(CustomStatus.INVALID_DATE);
        }

    }


    private void isValidPhoneNumber(String phoneNumber) {
        if(invalidatePhoneNumber(phoneNumber)){
            throw new UserNotValidException(CustomStatus.NOT_VALID_NO);
        }
    }

}

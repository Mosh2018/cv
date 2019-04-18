package com.netum.cv.backend.validation;

import com.netum.cv.backend.entity.AppBoolean;
import com.netum.cv.backend.modal.CvProfile;
import com.netum.cv.backend.modal.ValidationResult;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.netum.cv.backend.validation.AppSetting.*;
import static com.netum.cv.backend.validation.UserValidationUtil.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@Component
public class CvValidation {

    public List<ValidationResult> validateProfile(CvProfile cvProfile) throws ParseException{
        List<ValidationResult> results = new ArrayList<>();
        isValidPhoneNumber(cvProfile.getPhoneNumber(), results);
        isValidBirthday(cvProfile.getBirthday(), results);
        isValidNationality(cvProfile.getNationality(), results);
        isValidAddress(cvProfile.getAddress(), results);
        isValidCity(cvProfile.getCity(), results);
        isValidCountry(cvProfile.getCountry(), results);
        isValidZipCode(cvProfile.getZipCode(), results);
        return results;
    }

    private void isValidCountry(String country, List<ValidationResult> results) {
        AppBoolean valid = isValidStringValue(country, country_min.toInt(), country_max.toInt());
        if (valid.isTrue() == false) results.add(ValidationResult.build("the country " + valid.getCause(), NO_CONTENT));

    }

    private void isValidZipCode(String zipCode, List<ValidationResult> results) {
        AppBoolean valid = isValidStringValue(zipCode, zipCode_min.toInt(), zipCode_max.toInt());
        if (valid.isTrue() == false) results.add(ValidationResult.build("the zip " + valid.getCause(), NO_CONTENT));
    }

    private void isValidCity(String city, List<ValidationResult> results) {
        AppBoolean valid = isValidStringValue(city, city_min.toInt(), city_max.toInt());
        if (valid.isTrue() == false) results.add(ValidationResult.build("the city " + valid.getCause(), NO_CONTENT));

    }

    private void isValidAddress(String address, List<ValidationResult> results) {
        AppBoolean valid = isValidStringValue(address, address_min.toInt(), address_max.toInt());
        if(valid.isTrue() == false) results.add(ValidationResult.build("the address " + valid.getCause(), NO_CONTENT));
    }

    private void isValidNationality(String nationality, List<ValidationResult> results) {
        AppBoolean valid = isValidStringValue(nationality, nationality_min.toInt(), nationality_max.toInt());
        if (valid.isTrue() == false) results.add(ValidationResult.build("the nationality " + valid.getCause(), NO_CONTENT));
    }

    private void isValidBirthday(String birthday, List<ValidationResult> results) throws ParseException {
        AppBoolean invalid = inValidBirthdayFormat(birthday);
        if(invalid.isTrue()) results.add(ValidationResult.build(invalid.getCause(), BAD_REQUEST));
        Date now = getNowWithoutTime();
        Date userBirthday = new SimpleDateFormat("dd.MM.yyyy").parse(birthday);
        if (userBirthday.equals(now) || userBirthday.after(now))
            results.add(ValidationResult.build("birthday can not be in future", BAD_REQUEST));
    }

    private void isValidPhoneNumber(String phoneNumber, List<ValidationResult> results) {
        AppBoolean invalid = invalidatePhoneNumber(phoneNumber);
        if(invalid.isTrue()){
            results.add(ValidationResult.build(invalid.getCause(), BAD_REQUEST));
        }
    }
}

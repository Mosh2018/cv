package com.netum.cv.backend.validation;

import com.netum.cv.backend.modal.CvProfile;
import com.netum.cv.backend.modal.ValidationResult;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CvValidationTest extends TestBaseForValidation {

    private CvProfile cvProfile;
    private String phoneNumber = "040 8989 123";
    private String birthday = "01.02.1990";
    private String nationality = "Finnish";
    private String address = "Some where in Finland";
    private String country = "Finland";
    private String city = "Tampere";
    private String zipCode = "33500";


    @Test
    public void validateProfile() throws ParseException{

        List<ValidationResult> results = cvValidation.validateProfile(getCvProfile());
        assertEquals(0, results.size());

    }

    @Test
    public void birthdayCanNotBeInFuture() throws ParseException{
        this.birthday = getBirthday(1);
        List<ValidationResult> results;
        results = cvValidation.validateProfile(getCvProfile());
        assertEquals(1, results.size());
        assertEquals("birthday can not be in future", results.get(0).getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, results.get(0).getCustomStatus());
    }
    @Test
    public void birthdayCanNotBeToday() throws ParseException {
        this.birthday = getBirthday(0);
        List<ValidationResult> results;
        results = cvValidation.validateProfile(getCvProfile());
        assertEquals(1, results.size());
        assertEquals("birthday can not be in future", results.get(0).getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, results.get(0).getCustomStatus());
        }

    private String getBirthday(int years) throws ParseException {
        final String DATE_FORMAT = "dd.MM.yyyy";
        final DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

        LocalDateTime today = LocalDateTime.now();
        LocalDateTime localBirthday = today.plusYears(years);

        Date birthday = Date.from(localBirthday.atZone(ZoneId.systemDefault()).toInstant());

        return dateFormat.format(birthday);
    }

    private CvProfile getCvProfile() {
        cvProfile = new CvProfile();
        cvProfile.setAddress(address);
        cvProfile.setBirthday(birthday);
        cvProfile.setNationality(nationality);
        cvProfile.setPhoneNumber(phoneNumber);
        cvProfile.setCountry(country);
        cvProfile.setCity(city);
        cvProfile.setZipCode(zipCode);
        return cvProfile;
    }
}

package com.netum.cv.backend.validation;

import com.netum.cv.backend.exceptions.UserNotValidException;
import com.netum.cv.backend.modal.CustomStatus;
import com.netum.cv.backend.modal.CvProfile;
import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.Assert.*;

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
        assertTrue(cvValidation.validateProfile(getCvProfile()).getStatus().equals(CustomStatus.PASS_VALIDATION));

    }

    @Test
    public void birthdayCanNotBeInFuture() throws ParseException{
        this.birthday = getBirthday(1);
        try {
            assertTrue(cvValidation.validateProfile(getCvProfile()).getStatus().equals(CustomStatus.INVALID_DATE));
            fail();
        } catch (UserNotValidException exc) {
            assertTrue(exc.status.equals(CustomStatus.INVALID_DATE));
        }
    }

    @Test
    public void birthdayCanNotBeToday() throws ParseException {
        this.birthday = getBirthday(0);
        try {
            assertTrue(cvValidation.validateProfile(getCvProfile()).getStatus().equals(CustomStatus.INVALID_DATE));
            fail();
        } catch (UserNotValidException exc) {
            assertTrue(exc.status.equals(CustomStatus.INVALID_DATE));
        }
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

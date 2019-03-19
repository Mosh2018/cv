package com.netum.cv.backend.validation;

import com.netum.cv.backend.exceptions.UserNotValidException;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.netum.cv.backend.modal.CustomStatus.*;

public class UserValidationUtil {


    public static boolean isValidStringValue(String value, int min, int max) {
        if(value == null) {
            throw new UserNotValidException(EMPTY);
        }
        if (min > 0 && value.isEmpty()) {
            throw new UserNotValidException(EMPTY);
        }
        if (value.length() < min) {
            throw new UserNotValidException(SHORT_STRING);
        }
        if (value.length() > max) {
            throw new UserNotValidException(LONG_STRING);
        }

        return true;
    }

    public static int calculatePasswordStrength(String password) {

        int iPasswordScore = 0;

        if( password.length() < 8 )
            return 0;
        else if( password.length() >= 10 )
            iPasswordScore += 2;
        else
            iPasswordScore += 1;

        //if it contains one digit, add 2 to total score
        if( password.matches("(?=.*[0-9]).*") )
            iPasswordScore += 2;

        //if it contains one lower case letter, add 2 to total score
        if( password.matches("(?=.*[a-z]).*") )
            iPasswordScore += 2;

        //if it contains one upper case letter, add 2 to total score
        if( password.matches("(?=.*[A-Z]).*") )
            iPasswordScore += 2;

        //if it contains one special character, add 2 to total score
        if( password.matches("(?=.*[~!@#$%^&*()_-]).*") )
            iPasswordScore += 2;

        return iPasswordScore;
    }



    public static boolean invalidEmail(String email) {
        Matcher matcher = Pattern
                .compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE)
                .matcher(email);

        return !matcher.find();
    }

    public static boolean invalidatePhoneNumber(String phoneNumber) {
        String spaceRemovedString = phoneNumber.replace(" ", "");
        Matcher matcher = Pattern.compile("^\\d{7,16}$").matcher(spaceRemovedString);

        return !matcher.find();
    }

    public static boolean inValidBirthdayFormat(String birthday) {
        String spaceRemovedString = birthday.replace(" ", "");
        Matcher matcher = Pattern.compile("^\\d{2}.\\d{2}.\\d{4}$").matcher(spaceRemovedString);

        return !matcher.find();
    }


    public static Date getNowWithoutTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static boolean isEmptyString(String str) {
        if(str == null || str == "") {
            return true;
        }
        return false;
    }
}

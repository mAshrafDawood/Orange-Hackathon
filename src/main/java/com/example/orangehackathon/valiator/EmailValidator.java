package com.example.orangehackathon.valiator;

import java.util.regex.Pattern;

public class EmailValidator {

    /**
     * Allows numeric from 0 to 9.
     * both upper case and lower case letters are allowed.
     * allowed underscores, hyphens and dots.
     * Dots are not allowed as a starting or ending characters
     * Consecutive dots are not allowed.
     * local part length must be less than or equal to 64 characters
     * */
    private static final String EMAIL_REGEX = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";


    public static boolean isValid(String email){
        return Pattern.compile(EMAIL_REGEX).matcher(email).matches();
    }
}

package com.example.orangehackathon.exceptions;

public enum Errors {
    USER_CREDENTIALS_ERROR("0001", "USER CREDENTIALS ERROR"), //at login
    USER_IS_MISSING("0011", "User body is missing"), //when request body is empty
    USER_EMAIL_ALREADY_EXIST("0100", "User email already exist"), //at registration (done)
    FAILED_TO_UPLOAD_IMAGE("0111", "Failed to upload image"), //at add image in product service,
    REEL_IS_MISSING("1000", "Failed to retrive reel");

    private final String code;
    private final String message;

    Errors(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
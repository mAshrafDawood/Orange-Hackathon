package com.example.orangehackathon.exceptions;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ErrorResponse {
    private String errorCode;
    private String errorMessage;

}

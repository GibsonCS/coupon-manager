package com.onebrain.onebrain.exception;

public class ApiResponseError {

    private int statusCode;
    private String message;

    public ApiResponseError(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }
}

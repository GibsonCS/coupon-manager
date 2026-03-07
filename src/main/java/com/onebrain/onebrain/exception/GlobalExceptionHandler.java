package com.onebrain.onebrain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponseError> handleBusinessException(BusinessException businessException) {
        ApiResponseError apiResponseError = new ApiResponseError(
                HttpStatus.BAD_REQUEST.value(),
                businessException.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(apiResponseError);
    }
}

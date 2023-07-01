package com.blusalt.dronemgtsystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.blusalt.dronemgtsystem.utils.DroneConstant;
import com.blusalt.dronemgtsystem.utils.ErrorResponse;
import com.blusalt.dronemgtsystem.utils.Response;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final int BAD_REQUEST_CODE = HttpStatus.BAD_REQUEST.value();
    private final int INTERNAL_SERVER_ERROR_CODE = HttpStatus.INTERNAL_SERVER_ERROR.value();

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<Response<?>> handleInvalidRequestException(InvalidRequestException exception) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(String.valueOf(BAD_REQUEST_CODE))
                .description(exception.getMessage())
                .build();
        Response<?> response = Response.getResponse(null, errorResponse, false, DroneConstant.INVALID_REQUEST,
                BAD_REQUEST_CODE);
        return ResponseEntity.status(BAD_REQUEST_CODE).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response<?>> handleGenericException(InvalidRequestException exception) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(String.valueOf(INTERNAL_SERVER_ERROR_CODE))
                .description(exception.getMessage())
                .build();
        Response<?> response = Response.getResponse(null, errorResponse, false, DroneConstant.SERVER_ERROR,
                INTERNAL_SERVER_ERROR_CODE);
        return ResponseEntity.status(INTERNAL_SERVER_ERROR_CODE).body(response);
    }

}

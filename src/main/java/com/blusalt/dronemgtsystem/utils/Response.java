package com.blusalt.dronemgtsystem.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Response<T> {

    private boolean status;
    private String message;
    private int code;
    private T data;
    private ErrorResponse error;


    public static <T> Response<T> getResponse(T data, ErrorResponse error, boolean status, String message, int code) {
        return Response.<T>builder()
                .status(status)
                .message(message)
                .code(code)
                .data(data)
                .error(error)
                .build();
    }

    
}

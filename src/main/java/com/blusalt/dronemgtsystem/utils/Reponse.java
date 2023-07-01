package com.blusalt.dronemgtsystem.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Reponse<T> {

    private boolean status;
    private String message;
    private String code;
    private T data;
    private ErrorResponse error;

    
}

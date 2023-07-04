package com.blusalt.dronemgtsystem.utils;

import org.springframework.http.HttpStatus;

public class DroneConstant {

    public static final String INVALID_REQUEST = "Sorry, it appears that the request supplied is invalid";
    public static final String SERVER_ERROR = "It appears that something went wrong during the operation, please try again";
    public static final String SUCCESS = "Operation completed successfully";
    public static final String NOT_FOUND = "The item you requested is not found";
    public static final int SUCCESS_CODE = HttpStatus.OK.value();
    public static final String FAIL = "The operation failed";


    private DroneConstant() {}
    
}

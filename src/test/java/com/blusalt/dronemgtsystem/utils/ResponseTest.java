package com.blusalt.dronemgtsystem.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class ResponseTest {
    @Test
    public void getResponse_ValidArguments_ShouldCreateResponseObject() {
        // Arrange
        String message = "Success";
        int code = 200;
        boolean status = true;
        ErrorResponse error = null;
        Object data = new Object();

        // Act
        Response<Object> response = Response.getResponse(data, error, status, message, code);

        // Assert
        assertEquals(status, response.isStatus());
        assertEquals(message, response.getMessage());
        assertEquals(code, response.getCode());
        assertEquals(data, response.getData());
        assertNull(response.getError());
    }

}

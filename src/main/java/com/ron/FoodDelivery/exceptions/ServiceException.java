package com.ron.FoodDelivery.exceptions;

import com.ron.FoodDelivery.utils.ResponseLayout;
import org.springframework.http.HttpStatus;

public class ServiceException extends RuntimeException {
    private final String message;
    private final HttpStatus status;

    public ServiceException(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    public ResponseLayout response() {
        return new ResponseLayout(null, message, status);
    }
}
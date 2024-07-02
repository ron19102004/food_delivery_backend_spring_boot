package com.ron.FoodDelivery.exceptions;

import com.ron.FoodDelivery.utils.ResponseLayout;
import org.springframework.http.HttpStatus;

public class EntityNotFoundException extends RuntimeException{
    private final String message;
    private final HttpStatus status;

    public EntityNotFoundException(String message,HttpStatus status) {
        this.message = message;
        this.status = status;
    }
    public ResponseLayout response(){
        return new ResponseLayout(null,message,status);
    }
}

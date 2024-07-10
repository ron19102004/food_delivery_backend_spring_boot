package com.ron.FoodDelivery.exceptions;

import com.ron.FoodDelivery.utils.ResponseLayout;
import org.springframework.http.HttpStatus;

public class EntityNotFoundException extends RuntimeException{
    private final String message;

    public EntityNotFoundException(String message) {
        super(message);
        this.message = message;
    }
    public ResponseLayout<Object> response(){
        return new ResponseLayout<>(null,message,false);
    }
}

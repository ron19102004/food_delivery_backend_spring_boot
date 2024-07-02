package com.ron.FoodDelivery.exceptions;

import com.ron.FoodDelivery.utils.ResponseLayout;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseLayout> exceptionHandle(Exception exception) {
        System.out.println(exception);
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                .body(new ResponseLayout(exception.getMessage(), "Error", HttpStatus.BAD_GATEWAY));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ResponseLayout> exceptionHandle(BadCredentialsException exception) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ResponseLayout("Password incorrect!", exception.getMessage(), HttpStatus.FORBIDDEN));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ResponseLayout> exceptionHandle(UsernameNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ResponseLayout("Username not found", exception.getMessage(), HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ResponseLayout> exceptionHandle(ServiceException exception) {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                .body(exception.response());
    }
}

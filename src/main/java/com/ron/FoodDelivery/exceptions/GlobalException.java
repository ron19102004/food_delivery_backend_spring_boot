package com.ron.FoodDelivery.exceptions;

import com.ron.FoodDelivery.utils.ConsoleUtil;
import com.ron.FoodDelivery.utils.ResponseLayout;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {
    private final ConsoleUtil log = ConsoleUtil.newConsoleLog (this.getClass());

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseLayout<Object>> exceptionHandle(Exception exception) {
        exception.printStackTrace();
        log.err(exception);
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                .body(new ResponseLayout<>(exception.getMessage(), exception.getMessage(), false));
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ResponseLayout<Object>> exceptionHandle(AuthorizationDeniedException exception) {
        log.err(exception);
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ResponseLayout<>("Access Denied!", exception.getMessage(), false));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ResponseLayout<Object>> exceptionHandle(BadCredentialsException exception) {
        log.err(exception);
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ResponseLayout<>("Password incorrect!", exception.getMessage(), false));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ResponseLayout<Object>> exceptionHandle(UsernameNotFoundException exception) {
        log.err(exception);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ResponseLayout<>("Username not found", exception.getMessage(), false));
    }
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ResponseLayout<Object>> exceptionHandle(EntityNotFoundException exception) {
        log.err(exception);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ResponseLayout<>("Entity not found", exception.getMessage(), false));
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ResponseLayout<Object>> exceptionHandle(ServiceException exception) {
        log.err(exception);
        return ResponseEntity.status(exception.getStatus())
                .body((new ResponseLayout<>(null, exception.getMessage(), false)));
    }
}

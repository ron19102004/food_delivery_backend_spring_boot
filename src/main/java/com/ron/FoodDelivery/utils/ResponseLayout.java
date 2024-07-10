package com.ron.FoodDelivery.utils;

import org.springframework.http.HttpStatus;

public record ResponseLayout<T>(
        T data,
        String message,
        Boolean status
) {
}

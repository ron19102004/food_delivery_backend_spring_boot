package com.ron.FoodDelivery.utils;

import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;

public record ResponseLayout(
        Object data,
        String message,
        HttpStatus httpStatus
) {
}

package com.ron.FoodDelivery.utils;

public record ResponseLayout<T>(
        T data,
        String message,
        Boolean status
) {
}

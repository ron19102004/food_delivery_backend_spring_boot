package com.ron.FoodDelivery.entities.user.dto;

public record ResponseTotalUserDto(
        Long totalsUser,
        Long totalsDeliver,
        Long totalsSeller
) {
}

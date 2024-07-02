package com.ron.FoodDelivery.auth.dto;

import com.ron.FoodDelivery.entities.user.UserEntity;

public record ResponseLoginDto(
        UserEntity user,
        Boolean two_factor_auth,
        String access_token
) {
}

package com.ron.FoodDelivery.auth.dto;

import org.hibernate.validator.constraints.Length;

public record RequestLoginDto(
        @Length(min = 5) String username,
        @Length(min = 8) String password
) {
}

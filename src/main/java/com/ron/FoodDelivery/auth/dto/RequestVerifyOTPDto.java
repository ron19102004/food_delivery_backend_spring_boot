package com.ron.FoodDelivery.auth.dto;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record RequestVerifyOTPDto(
        @NotNull @Length(min = 6, max = 6) String code,
        @NotNull String token
) {
}

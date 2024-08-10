package com.ron.FoodDelivery.entities.location.dto;

import jakarta.validation.constraints.NotNull;

public record RequestCreateLocationDto(
        @NotNull String name,
        @NotNull String code
) {
}

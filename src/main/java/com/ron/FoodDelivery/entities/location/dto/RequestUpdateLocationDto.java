package com.ron.FoodDelivery.entities.location.dto;

import jakarta.validation.constraints.NotNull;

public record RequestUpdateLocationDto(
        @NotNull String name,
        @NotNull Integer code
) {
}

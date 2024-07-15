package com.ron.FoodDelivery.entities.delivery_charges.dto;

import jakarta.validation.constraints.NotNull;

public record RequestCreateDeliveryChargesDto(
        @NotNull Double price,
        @NotNull Double kilometer,
        @NotNull Boolean is_active
) {
}

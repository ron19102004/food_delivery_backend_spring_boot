package com.ron.FoodDelivery.entities.request_role_account.dto;

import jakarta.validation.constraints.NotNull;

public record RequestHandleAccountDto(
        @NotNull Boolean is_accepted
) {
}

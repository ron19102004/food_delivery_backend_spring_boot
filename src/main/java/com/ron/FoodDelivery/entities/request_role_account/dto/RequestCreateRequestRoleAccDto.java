package com.ron.FoodDelivery.entities.request_role_account.dto;

import com.ron.FoodDelivery.entities.request_role_account.RequestRole;
import jakarta.validation.constraints.NotNull;

public record RequestCreateRequestRoleAccDto(
        @NotNull RequestRole role,
        @NotNull RequestCreateRequestRoleAccDataDto data
) {
}

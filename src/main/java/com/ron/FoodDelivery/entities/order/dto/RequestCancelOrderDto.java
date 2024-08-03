package com.ron.FoodDelivery.entities.order.dto;

import jakarta.validation.constraints.NotNull;

public record RequestCancelOrderDto(
        @NotNull String reason_cancel,
        @NotNull Long order_id
) {
}

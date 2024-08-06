package com.ron.FoodDelivery.entities.order.dto;

import com.ron.FoodDelivery.entities.order.OrderStatus;
import jakarta.validation.constraints.NotNull;

public record RequestCreateOrderDto(
        @NotNull Double latitude_receive,
        @NotNull Double longitude_receive,
        @NotNull Integer quantity,
        String note,
        @NotNull String address,
        String code_voucher
) {
}

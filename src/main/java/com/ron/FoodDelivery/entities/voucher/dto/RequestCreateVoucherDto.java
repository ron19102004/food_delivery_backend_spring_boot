package com.ron.FoodDelivery.entities.voucher.dto;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

public record RequestCreateVoucherDto(
        @NotNull Float percent,
        @NotNull String name,
        @NotNull Long quantity,
        @NotNull Date issued_at,
        @NotNull Date expired_at,
        @NotNull Long category_id,
        @NotNull @Length(min = 5, max = 15) String code
) {
}

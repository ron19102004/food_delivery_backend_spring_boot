package com.ron.FoodDelivery.entities.process.dto;

import com.ron.FoodDelivery.entities.process.ProcessStatus;
import jakarta.validation.constraints.NotNull;

public record RequestCreateProcessDto(
       @NotNull ProcessStatus status,
        String note
) {
}

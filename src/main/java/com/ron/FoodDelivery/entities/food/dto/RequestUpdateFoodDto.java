package com.ron.FoodDelivery.entities.food.dto;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record RequestUpdateFoodDto(
        @NotNull Long category_id,
        @NotNull String name,
        @NotNull @Length(min = 20) String description,
        @NotNull Double price,
        @NotNull Float sale_off

) {
}

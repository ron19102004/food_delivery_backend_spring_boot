package com.ron.FoodDelivery.entities.food.dto;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record RequestCreateFoodDto(
        @NotNull Long category_id,
        @NotNull String name,
        @NotNull @Length(min = 20) String description,
        @NotNull Double price,
        @NotNull String poster_url
) {
}

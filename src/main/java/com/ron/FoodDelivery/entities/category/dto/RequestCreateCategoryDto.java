package com.ron.FoodDelivery.entities.category.dto;

import jakarta.validation.constraints.NotNull;

public record RequestCreateCategoryDto(
       @NotNull String name
) {
}

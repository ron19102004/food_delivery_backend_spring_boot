package com.ron.FoodDelivery.entities.category.dto;

import jakarta.validation.constraints.NotNull;

public record RequestUpdateCategoryDto(
        @NotNull String name,
        @NotNull String image_url_drive
) {
}

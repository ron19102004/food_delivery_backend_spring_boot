package com.ron.FoodDelivery.entities.comment.dto;

import com.ron.FoodDelivery.entities.comment.Emotion;
import jakarta.validation.constraints.NotNull;

public record RequestCreateCommentDto(
        @NotNull String content,
        @NotNull Emotion emotion,
        @NotNull Long food_id
) {
}

package com.ron.FoodDelivery.entities.food.dto;

import com.ron.FoodDelivery.entities.comment.CommentEntity;
import com.ron.FoodDelivery.entities.food.FoodEntity;

import java.util.List;

public record ResponseDetailsFoodDto(
        FoodEntity food,
        List<CommentEntity> comments
) {
}

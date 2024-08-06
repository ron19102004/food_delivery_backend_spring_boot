package com.ron.FoodDelivery.entities.order.dto;

import com.ron.FoodDelivery.entities.order.OrderEntity;

import java.util.List;

public record ResponseAllOrderSellerDto(
        List<OrderEntity> handling,
        List<OrderEntity> canceled,
        List<OrderEntity> finished
) {
}

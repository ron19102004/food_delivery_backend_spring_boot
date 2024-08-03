package com.ron.FoodDelivery.entities.order.dto;

import com.ron.FoodDelivery.entities.order.OrderEntity;
import com.ron.FoodDelivery.entities.process.ProcessEntity;

import java.util.List;

public record ResponseInfoOrderDto(
        OrderEntity order,
        List<ProcessEntity> processEntities
) {
}

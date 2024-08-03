package com.ron.FoodDelivery.services;

import com.ron.FoodDelivery.entities.order.OrderEntity;
import com.ron.FoodDelivery.entities.process.ProcessEntity;
import com.ron.FoodDelivery.entities.process.ProcessStatus;
import com.ron.FoodDelivery.entities.process.dto.RequestCreateProcessDto;

import java.util.List;

public interface ProcessService {
    List<ProcessEntity> getAllByOrderId(Long orderId);
    ProcessEntity create(OrderEntity order, String note, ProcessStatus processStatus);
    ProcessEntity create(String username,Long order_id,RequestCreateProcessDto requestCreateProcessDto);
}

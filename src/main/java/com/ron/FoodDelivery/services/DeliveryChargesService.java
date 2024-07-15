package com.ron.FoodDelivery.services;

import com.ron.FoodDelivery.entities.delivery_charges.DeliveryChargesEntity;
import com.ron.FoodDelivery.entities.delivery_charges.dto.RequestCreateDeliveryChargesDto;

import java.util.List;

public interface DeliveryChargesService {
    DeliveryChargesEntity create(RequestCreateDeliveryChargesDto requestCreateDeliveryChargesDto);
    void changeActive(Long deliveryId);
    List<DeliveryChargesEntity> getAll();
}

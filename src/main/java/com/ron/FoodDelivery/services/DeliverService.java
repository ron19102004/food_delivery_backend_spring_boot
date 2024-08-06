package com.ron.FoodDelivery.services;

import com.ron.FoodDelivery.entities.deliver.DeliverEntity;
import com.ron.FoodDelivery.entities.deliver.dto.RequestUpdateInformationDeliverDto;
import com.ron.FoodDelivery.services.interfaces.futures.ICreateRequestRoleAccountService;

public interface DeliverService extends ICreateRequestRoleAccountService<DeliverEntity,RequestUpdateInformationDeliverDto> {
}

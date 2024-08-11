package com.ron.FoodDelivery.services;

import com.ron.FoodDelivery.entities.seller.SellerEntity;
import com.ron.FoodDelivery.entities.seller.dto.RequestUpdateInformationSellerDto;
import com.ron.FoodDelivery.services.interfaces.futures.ICreateRequestRoleAccountService;

public interface SellerService extends ICreateRequestRoleAccountService<SellerEntity, RequestUpdateInformationSellerDto> {
    SellerEntity findByUsername(String username);
    SellerEntity findById(Long id);
}

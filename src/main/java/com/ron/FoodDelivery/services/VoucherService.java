package com.ron.FoodDelivery.services;

import com.ron.FoodDelivery.entities.voucher.VoucherEntity;
import com.ron.FoodDelivery.entities.voucher.dto.RequestCreateVoucherDto;

import java.util.List;

public interface VoucherService {
    VoucherEntity create(String username,RequestCreateVoucherDto requestCreateVoucherDto);
    VoucherEntity createWithSystem(RequestCreateVoucherDto requestCreateVoucherDto);
    void changeHidden(String username,Long voucherId);
    List<VoucherEntity> findAllBySellerUsername(String seller_username);
    List<VoucherEntity> findAllOfSystem();
    List<VoucherEntity> findAllBySellerId(Long id);
}

package com.ron.FoodDelivery.services.interfaces.futures;

import com.ron.FoodDelivery.entities.request_role_account.dto.RequestCreateRequestRoleAccDataDto;
import com.ron.FoodDelivery.entities.user.UserEntity;

public interface ICreateRequestRoleAccountService {
    void disable_account(Long id);

    void init_account(UserEntity user, RequestCreateRequestRoleAccDataDto requestCreateRequestRoleAccDataDto);
}

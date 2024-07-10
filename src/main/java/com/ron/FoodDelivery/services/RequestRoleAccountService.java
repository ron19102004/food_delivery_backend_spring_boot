package com.ron.FoodDelivery.services;

import com.ron.FoodDelivery.entities.request_role_account.dto.RequestCreateRequestRoleAccDto;
import com.ron.FoodDelivery.entities.user.UserEntity;
import com.ron.FoodDelivery.utils.ResponseLayout;

public interface RequestRoleAccountService {
    ResponseLayout<Object> createRequestRoleAcc(UserEntity user, RequestCreateRequestRoleAccDto requestCreateRequestRoleAccDto);

    void cancelAccount(UserEntity user);

    void handle(Long requestRoleAccountId, boolean is_accepted);
}

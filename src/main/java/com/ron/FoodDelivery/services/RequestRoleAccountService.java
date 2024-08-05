package com.ron.FoodDelivery.services;

import com.ron.FoodDelivery.entities.request_role_account.RequestRoleAccount;
import com.ron.FoodDelivery.entities.request_role_account.dto.RequestCreateRequestRoleAccDto;
import com.ron.FoodDelivery.entities.request_role_account.dto.ResponseGetAllRequestRole;
import com.ron.FoodDelivery.entities.user.UserEntity;
import com.ron.FoodDelivery.utils.ResponseLayout;

public interface RequestRoleAccountService {
    ResponseLayout<RequestRoleAccount> createRequestRoleAcc(UserEntity user, RequestCreateRequestRoleAccDto requestCreateRequestRoleAccDto);

    void cancelAccount(UserEntity user);

    void handle(Long requestRoleAccountId, boolean is_accepted);
    ResponseGetAllRequestRole getAll();
}

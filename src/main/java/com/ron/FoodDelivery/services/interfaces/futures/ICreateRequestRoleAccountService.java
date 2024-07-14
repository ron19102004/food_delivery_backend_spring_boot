package com.ron.FoodDelivery.services.interfaces.futures;

import com.ron.FoodDelivery.entities.request_role_account.dto.RequestCreateRequestRoleAccDataDto;
import com.ron.FoodDelivery.entities.user.UserEntity;

public interface ICreateRequestRoleAccountService<UpdateDto> {
    void set_enable_account(Long id,Boolean enable);

    void init_account(UserEntity user, RequestCreateRequestRoleAccDataDto requestCreateRequestRoleAccDataDto);
    void update_information(String username,UpdateDto dto );
}

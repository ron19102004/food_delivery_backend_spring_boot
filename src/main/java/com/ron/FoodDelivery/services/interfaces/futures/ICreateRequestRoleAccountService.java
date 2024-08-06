package com.ron.FoodDelivery.services.interfaces.futures;

import com.ron.FoodDelivery.entities.request_role_account.dto.RequestCreateRequestRoleAccDataDto;
import com.ron.FoodDelivery.entities.user.UserEntity;
import org.springframework.web.multipart.MultipartFile;

public interface ICreateRequestRoleAccountService<Entity,UpdateDto> {
    void set_enable_account(Long id,Boolean enable);
    void init_account(UserEntity user, RequestCreateRequestRoleAccDataDto requestCreateRequestRoleAccDataDto);
    Entity update_information(String username,UpdateDto dto );
}

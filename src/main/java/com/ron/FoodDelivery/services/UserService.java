package com.ron.FoodDelivery.services;

import com.ron.FoodDelivery.auth.dto.RequestRegisterDto;
import com.ron.FoodDelivery.entities.user.UserEntity;
import com.ron.FoodDelivery.entities.user.UserRole;

public interface UserService {
    UserEntity findByUsername(String username);

    UserEntity save(UserRole role, RequestRegisterDto requestRegisterDto);
    void changeRole(Long userId,UserRole role);
}

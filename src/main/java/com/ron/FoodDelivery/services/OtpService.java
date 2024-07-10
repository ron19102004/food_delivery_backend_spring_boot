package com.ron.FoodDelivery.services;

import com.ron.FoodDelivery.auth.dto.ResponseLoginDto;
import com.ron.FoodDelivery.entities.user.UserEntity;
import com.ron.FoodDelivery.utils.ResponseLayout;

public interface OtpService {
    String generate_otp(UserEntity user);
    ResponseLayout<ResponseLoginDto> is_valid(Long user_id, String otp);
}

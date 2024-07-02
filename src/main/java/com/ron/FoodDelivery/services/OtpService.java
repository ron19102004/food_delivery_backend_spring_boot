package com.ron.FoodDelivery.services;

import com.ron.FoodDelivery.entities.user.UserEntity;
import com.ron.FoodDelivery.utils.ResponseLayout;

public interface OtpService {
    String generate_otp(UserEntity user);
    ResponseLayout is_valid(Long user_id, String otp);
}

package com.ron.FoodDelivery.auth;

import com.ron.FoodDelivery.auth.dto.RequestLoginDto;
import com.ron.FoodDelivery.auth.dto.RequestRegisterDto;
import com.ron.FoodDelivery.auth.dto.RequestVerifyOTPDto;
import com.ron.FoodDelivery.auth.dto.ResponseLoginDto;
import com.ron.FoodDelivery.entities.token.UserAgent;
import com.ron.FoodDelivery.entities.user.UserEntity;
import com.ron.FoodDelivery.utils.ResponseLayout;

public interface AuthService {
    ResponseLayout<ResponseLoginDto> login(RequestLoginDto requestLoginDto, UserAgent userAgent);
    ResponseLayout<UserEntity> register(RequestRegisterDto requestRegisterDto);
    ResponseLayout<ResponseLoginDto> verify_otp(RequestVerifyOTPDto requestVerifyOTPDto, UserAgent userAgent);
    ResponseLayout<Boolean> change_tfa(String username);
    UserEntity findUserByUsername(String username);
}

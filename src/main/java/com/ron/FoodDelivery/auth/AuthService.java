package com.ron.FoodDelivery.auth;

import com.ron.FoodDelivery.auth.dto.RequestLoginDto;
import com.ron.FoodDelivery.auth.dto.RequestRegisterDto;
import com.ron.FoodDelivery.auth.dto.RequestVerifyOTPDto;
import com.ron.FoodDelivery.utils.ResponseLayout;

public interface AuthService {
    ResponseLayout login(RequestLoginDto requestLoginDto);
    ResponseLayout register(RequestRegisterDto requestRegisterDto);
    ResponseLayout verify_otp(RequestVerifyOTPDto requestVerifyOTPDto);
    ResponseLayout change_tfa(String username);
}

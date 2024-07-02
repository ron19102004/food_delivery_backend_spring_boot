package com.ron.FoodDelivery.services;

import com.ron.FoodDelivery.entities.token.TokenEntity;
import com.ron.FoodDelivery.entities.token.UserAgent;
import com.ron.FoodDelivery.entities.user.UserEntity;

public interface TokenService {
    void saveToken(UserEntity user, String token, UserAgent userAgent);
    void deleteByToken(String token);
    TokenEntity findByToken(String token);
}

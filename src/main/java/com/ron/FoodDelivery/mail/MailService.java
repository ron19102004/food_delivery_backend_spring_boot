package com.ron.FoodDelivery.mail;

import com.ron.FoodDelivery.entities.user.UserEntity;

public interface MailService {
    void send_otp(UserEntity user, String otp);
}

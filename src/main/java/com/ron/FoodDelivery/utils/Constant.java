package com.ron.FoodDelivery.utils;

import org.springframework.beans.factory.annotation.Value;

public abstract class Constant {
    @Value("${constant.images.default.avatar}")
    public static String AVATAR_DEFAULT_URL;
    @Value("${constant.images.default.category}")
    public static String CATEGORY_DEFAULT_URL;
    @Value("${constant.images.default.food}")
    public static String FOOD_POSTER_DEFAULT_URL;

}

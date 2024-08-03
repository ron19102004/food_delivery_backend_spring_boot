package com.ron.FoodDelivery.services;

public interface LikeService {
    void like(String username,Long foodId);
    void unlike(String username, Long foodId);
    int totalLike(Long foodId);
}

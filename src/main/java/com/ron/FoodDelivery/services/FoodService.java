package com.ron.FoodDelivery.services;

import com.ron.FoodDelivery.entities.food.FoodEntity;
import com.ron.FoodDelivery.entities.food.dto.RequestCreateFoodDto;
import com.ron.FoodDelivery.entities.food.dto.RequestUpdateFoodDto;
import com.ron.FoodDelivery.entities.food.dto.ResponseDetailsFoodDto;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;


public interface FoodService {
    FoodEntity create(String usernameSeller, RequestCreateFoodDto requestCreateFoodDto);

    FoodEntity update(String usernameSeller, Long food_id, RequestUpdateFoodDto requestUpdateFoodDto);

    Page<FoodEntity> findByCategoryIdWithPage(Long category_id, int pageNumber);

    Page<FoodEntity> findBySellerIdWithPage(Long sellerId, int pageNumber);
    Page<FoodEntity> findBySellerUsernameWithPage(String username, int pageNumber);

    ResponseDetailsFoodDto getDetailsFood(Long food_id);
    void deleteById(Long id, String usernameSeller);
}

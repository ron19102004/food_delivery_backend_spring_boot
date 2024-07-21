package com.ron.FoodDelivery.services;

import com.ron.FoodDelivery.entities.category.CategoryEntity;
import com.ron.FoodDelivery.entities.category.dto.RequestCreateCategoryDto;
import org.springframework.web.multipart.MultipartFile;

public interface CategoryService {
    CategoryEntity create(RequestCreateCategoryDto requestCreateCategoryDto);
    CategoryEntity updateImage(Long categoryId, MultipartFile file);
    void remove(Long categoryId);
}

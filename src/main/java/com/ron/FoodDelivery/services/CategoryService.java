package com.ron.FoodDelivery.services;

import com.ron.FoodDelivery.entities.category.CategoryEntity;
import com.ron.FoodDelivery.entities.category.dto.RequestCreateCategoryDto;
import com.ron.FoodDelivery.entities.category.dto.RequestUpdateCategoryDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CategoryService {
    CategoryEntity create(RequestCreateCategoryDto requestCreateCategoryDto);
    CategoryEntity updateImage(Long categoryId, RequestUpdateCategoryDto requestUpdateCategoryDto);
    void remove(Long categoryId);
    List<CategoryEntity> getAll();
}

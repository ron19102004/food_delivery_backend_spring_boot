package com.ron.FoodDelivery.repositories;

import com.ron.FoodDelivery.entities.category.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity,Long> {
    CategoryEntity findByIdAndDeleted(Long id, Boolean deleted);
}

package com.ron.FoodDelivery.repositories;

import com.ron.FoodDelivery.entities.like.LikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<LikeEntity,Long> {
    @Query("SELECT l FROM LikeEntity l WHERE l.user.username = :username AND l.food.id = :foodId")
    LikeEntity findByUsernameAndFoodId(@Param("username") String username,@Param("foodId") Long foodId);
    @Query("SELECT COUNT(l.id) FROM LikeEntity l WHERE l.food.id = :foodId")
    int totalLikeByFoodId(@Param("foodId") Long foodId);
}

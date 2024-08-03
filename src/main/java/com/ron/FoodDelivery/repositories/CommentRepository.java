package com.ron.FoodDelivery.repositories;

import com.ron.FoodDelivery.entities.comment.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    @Query("SELECT c FROM CommentEntity c WHERE c.order.food.id = :foodId ORDER BY c.id DESC")
    List<CommentEntity> findByFoodId(@Param("foodId") Long foodId);
}

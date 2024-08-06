package com.ron.FoodDelivery.repositories;

import com.ron.FoodDelivery.entities.food.FoodEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodRepository extends JpaRepository<FoodEntity, Long> {
    FoodEntity findByIdAndDeleted(Long id, Boolean deleted);
    @Query("SELECT f FROM FoodEntity f WHERE f.category.id = :categoryId AND f.deleted = :deleted")
    Page<FoodEntity> findAllByCategoryId(@Param("categoryId") Long categoryId, @Param("deleted") Boolean deleted, Pageable pageable);
    @Query("SELECT f FROM FoodEntity f WHERE f.seller.id = :sellerId AND f.deleted = :deleted")
    Page<FoodEntity> findAllBySellerId(@Param("sellerId") Long sellerId, @Param("deleted") Boolean deleted, Pageable pageable);
    @Query("SELECT f FROM FoodEntity f WHERE f.seller.user.username = :username AND f.deleted = :deleted")
    Page<FoodEntity> findAllBySellerUsername(@Param("username") String username, @Param("deleted") Boolean deleted, Pageable pageable);
}

package com.ron.FoodDelivery.repositories;

import com.ron.FoodDelivery.entities.seller.SellerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerRepository extends JpaRepository<SellerEntity, Long> {
    @Query("SELECT s FROM SellerEntity s WHERE s.user.username = :username")
    SellerEntity findByUsername(@Param("username") String username);
}

package com.ron.FoodDelivery.repositories;

import com.ron.FoodDelivery.entities.seller.SellerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerRepository extends JpaRepository<SellerEntity, Long> {
    @Query("SELECT s FROM SellerEntity s WHERE s.user.username = :username AND s.enabled = :enabled")
    SellerEntity findByUsernameAndEnabled(@Param("username") String username, @Param("enabled") Boolean enabled);
    SellerEntity findByIdAndEnabled(Long id, Boolean enabled);
    @Query("SELECT COUNT(s.id) FROM SellerEntity s WHERE s.enabled = TRUE")
    Long totalsSeller();
}

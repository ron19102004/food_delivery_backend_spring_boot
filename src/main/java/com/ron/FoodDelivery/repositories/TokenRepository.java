package com.ron.FoodDelivery.repositories;

import com.ron.FoodDelivery.entities.token.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TokenRepository extends JpaRepository<TokenEntity, Long> {
    @Query("SELECT t FROM TokenEntity t WHERE t.user.id = :user_id ORDER BY t.id ASC")
    List<TokenEntity> findAllByUserId(@Param("user_id") Long user_id);

    @Query("SELECT t FROM TokenEntity t WHERE t.access_token = :access_token")
    TokenEntity findByAccessToken(@Param("access_token") String access_token);
}

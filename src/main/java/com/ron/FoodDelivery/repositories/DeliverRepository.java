package com.ron.FoodDelivery.repositories;

import com.ron.FoodDelivery.entities.deliver.DeliverEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliverRepository extends JpaRepository<DeliverEntity, Long> {
    @Query("SELECT d FROM DeliverEntity d WHERE d.user.username = :username AND d.enabled = :enabled")
    DeliverEntity findByUsername(@Param("username")String username,@Param("enabled") Boolean enabled);
}

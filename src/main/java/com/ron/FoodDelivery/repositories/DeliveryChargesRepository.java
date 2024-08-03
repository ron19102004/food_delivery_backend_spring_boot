package com.ron.FoodDelivery.repositories;

import com.ron.FoodDelivery.entities.delivery_charges.DeliveryChargesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeliveryChargesRepository extends JpaRepository<DeliveryChargesEntity, Long> {
    @Query("SELECT d FROM DeliveryChargesEntity d WHERE d.is_active = :is_active")
    List<DeliveryChargesEntity> findAllByIsActive(@Param("is_active") Boolean is_active);
    @Query("SELECT d FROM DeliveryChargesEntity d WHERE d.kilometer < :kilometer ORDER BY d.kilometer DESC")
    Optional<DeliveryChargesEntity> findByKm(@Param("kilometer") Double kilometer);
}

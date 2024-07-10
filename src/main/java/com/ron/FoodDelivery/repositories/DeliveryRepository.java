package com.ron.FoodDelivery.repositories;

import com.ron.FoodDelivery.entities.deliver.DeliverEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryRepository extends JpaRepository<DeliverEntity, Long> {
}

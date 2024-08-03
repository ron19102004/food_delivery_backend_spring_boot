package com.ron.FoodDelivery.repositories;

import com.ron.FoodDelivery.entities.process.ProcessEntity;
import com.ron.FoodDelivery.entities.process.ProcessStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProcessRepository extends JpaRepository<ProcessEntity,Long> {
    @Query("SELECT p FROM ProcessEntity p WHERE p.order.id = :orderId ORDER BY p.id DESC")
    List<ProcessEntity> findAllByOrderId(@Param("orderId") Long orderId);
    @Query("SELECT p FROM ProcessEntity p WHERE p.order.id = :orderId AND p.status = :status")
    ProcessEntity findByOrderIdAndStatus(@Param("orderId") Long orderId,@Param("status") ProcessStatus processStatus);
}

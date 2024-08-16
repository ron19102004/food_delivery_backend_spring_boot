package com.ron.FoodDelivery.repositories;

import com.ron.FoodDelivery.entities.order.OrderEntity;
import com.ron.FoodDelivery.entities.order.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    OrderEntity findByIdAndStatus(Long id, OrderStatus orderStatus);
    @Query("SELECT o FROM OrderEntity o WHERE o.status = :status AND o.food.seller.user.username = :username ORDER BY o.id DESC")
    List<OrderEntity> findAllBySellerUsernameWithStatus(@Param("status") OrderStatus orderStatus,@Param("username")String username);
    @Query("SELECT o FROM OrderEntity o WHERE o.user.username = :username ORDER BY o.id DESC")
    List<OrderEntity> findAllMyOrderByUsername(@Param("username") String username);
}

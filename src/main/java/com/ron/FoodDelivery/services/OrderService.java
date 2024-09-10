package com.ron.FoodDelivery.services;

import com.ron.FoodDelivery.entities.order.OrderEntity;
import com.ron.FoodDelivery.entities.order.dto.RequestCancelOrderDto;
import com.ron.FoodDelivery.entities.order.dto.RequestCreateOrderDto;
import com.ron.FoodDelivery.entities.order.dto.ResponseAllOrderSellerDto;
import com.ron.FoodDelivery.entities.order.dto.ResponseInfoOrderDto;
import com.ron.FoodDelivery.entities.process.ProcessEntity;

import java.util.List;

public interface OrderService {
    OrderEntity create(Long foodId,
                       String username,
                       RequestCreateOrderDto requestCreateOrderDto);
    OrderEntity updateDeliver(Long orderId,String usernameDeliver);
    OrderEntity getOrderById(Long orderId);
    ResponseInfoOrderDto getInfoOrder(Long order_id,String username);
    void cancelOrder(String username, RequestCancelOrderDto requestCancelOrderDto);
    ResponseAllOrderSellerDto getOrdersBySellerUsername(String username);
    List<OrderEntity> userOrdersByUsername(String username);
    List<OrderEntity> getOrdersForDeliver(String location_code);
}

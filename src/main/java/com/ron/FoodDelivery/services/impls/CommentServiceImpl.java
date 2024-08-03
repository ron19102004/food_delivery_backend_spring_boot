package com.ron.FoodDelivery.services.impls;

import com.ron.FoodDelivery.entities.comment.CommentEntity;
import com.ron.FoodDelivery.entities.comment.dto.RequestCreateCommentDto;
import com.ron.FoodDelivery.entities.order.OrderEntity;
import com.ron.FoodDelivery.entities.order.OrderStatus;
import com.ron.FoodDelivery.exceptions.EntityNotFoundException;
import com.ron.FoodDelivery.exceptions.ServiceException;
import com.ron.FoodDelivery.repositories.CommentRepository;
import com.ron.FoodDelivery.repositories.OrderRepository;
import com.ron.FoodDelivery.repositories.UserRepository;
import com.ron.FoodDelivery.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public void create(String username, Long order_id, RequestCreateCommentDto requestCreateCommentDto) {
        OrderEntity orderEntity = orderRepository.findByIdAndStatus(order_id, OrderStatus.FINISHED);
        if (orderEntity == null) throw new EntityNotFoundException("Order not found");
        if (!orderEntity.getUser().getUsername().equals(username))
            throw new ServiceException("Not permission", HttpStatus.FORBIDDEN);
        CommentEntity commentEntity = CommentEntity.builder()
                .content(requestCreateCommentDto.content())
                .emotion(requestCreateCommentDto.emotion())
                .order(orderEntity)
                .code_order(orderEntity.getCode_order())
                .build();
        commentRepository.save(commentEntity);
    }
}

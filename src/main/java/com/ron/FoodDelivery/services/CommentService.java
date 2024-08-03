package com.ron.FoodDelivery.services;

import com.ron.FoodDelivery.entities.comment.dto.RequestCreateCommentDto;

public interface CommentService {
    void create(String username,Long order_id, RequestCreateCommentDto requestCreateCommentDto);
}

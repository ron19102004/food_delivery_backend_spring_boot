package com.ron.FoodDelivery.controllers;

import com.ron.FoodDelivery.entities.comment.dto.RequestCreateCommentDto;
import com.ron.FoodDelivery.entities.order.OrderEntity;
import com.ron.FoodDelivery.entities.order.dto.RequestCancelOrderDto;
import com.ron.FoodDelivery.entities.order.dto.RequestCreateOrderDto;
import com.ron.FoodDelivery.entities.order.dto.ResponseInfoOrderDto;
import com.ron.FoodDelivery.entities.process.ProcessEntity;
import com.ron.FoodDelivery.entities.process.dto.RequestCreateProcessDto;
import com.ron.FoodDelivery.services.CommentService;
import com.ron.FoodDelivery.services.OrderService;
import com.ron.FoodDelivery.services.ProcessService;
import com.ron.FoodDelivery.utils.PreAuthUtil;
import com.ron.FoodDelivery.utils.ResponseLayout;
import com.ron.FoodDelivery.utils.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/foods/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProcessService processService;
    @Autowired
    private CommentService commentService;

    @PreAuthorize(PreAuthUtil.hasUSER)
    @PostMapping("/{order_id}/comment")
    public ResponseEntity<ResponseLayout<Object>> createOrder(@PathVariable("order_id") Long order_id, @RequestBody RequestCreateCommentDto requestCreateCommentDto) {
        Authentication authentication = SecurityUtil.authentication();
        commentService.create(authentication.getName(), order_id, requestCreateCommentDto);
        return ResponseEntity.ok(new ResponseLayout<>(null, "Created!", true));
    }

    @PreAuthorize(PreAuthUtil.hasUSER)
    @PostMapping("/new/{food_id}")
    public ResponseEntity<ResponseLayout<OrderEntity>> createOrder(@PathVariable("food_id") Long food_id, @RequestBody RequestCreateOrderDto requestCreateOrderDto) {
        Authentication authentication = SecurityUtil.authentication();
        OrderEntity orderEntity = orderService.create(food_id, authentication.getName(), requestCreateOrderDto);
        return ResponseEntity.ok(new ResponseLayout<>(orderEntity, "Created!", true));
    }

    @PreAuthorize(PreAuthUtil.hasDELIVER_SELLER_USER)
    @GetMapping("/{order_id}")
    public ResponseEntity<ResponseLayout<ResponseInfoOrderDto>> getInfoOrder(@PathVariable("order_id") Long order_id) {
        Authentication authentication = SecurityUtil.authentication();
        return ResponseEntity.ok(new ResponseLayout<>(orderService.getInfoOrder(order_id, authentication.getName()), "Got!", true));
    }
    @PreAuthorize(PreAuthUtil.hasUSER)
    @GetMapping("/my")
    public ResponseEntity<ResponseLayout<List<OrderEntity>>> getMyOrders() {
        Authentication authentication = SecurityUtil.authentication();
        return ResponseEntity.ok(new ResponseLayout<>(orderService.userOrdersByUsername(authentication.getName()), "Got!", true));
    }
    @PreAuthorize(PreAuthUtil.hasDELIVER)
    @PatchMapping("/update-deliver/{order_id}")
    public ResponseEntity<ResponseLayout<OrderEntity>> updateDeliver(@PathVariable("order_id") Long order_id) {
        Authentication authentication = SecurityUtil.authentication();
        OrderEntity orderEntity = orderService.updateDeliver(order_id, authentication.getName());
        return ResponseEntity.ok(new ResponseLayout<>(orderEntity, "Update deliver!", true));
    }

    @PreAuthorize(PreAuthUtil.hasDELIVER)
    @PostMapping("/deliver/update-process/{order_id}")
    public ResponseEntity<ResponseLayout<ProcessEntity>> createProcess(@PathVariable("order_id") Long order_id, @RequestBody RequestCreateProcessDto requestCreateProcessDto) {
        Authentication authentication = SecurityUtil.authentication();
        ProcessEntity process = processService.create(authentication.getName(), order_id, requestCreateProcessDto);
        return ResponseEntity.ok(new ResponseLayout<>(process, "Created!", true));
    }

    @PreAuthorize(PreAuthUtil.hasUSER_SELLER)
    @PostMapping("/cancel")
    public ResponseEntity<ResponseLayout<Object>> cancelOrder(@RequestBody RequestCancelOrderDto requestCancelOrderDto) {
        Authentication authentication = SecurityUtil.authentication();
        orderService.cancelOrder(authentication.getName(), requestCancelOrderDto);
        return ResponseEntity.ok(new ResponseLayout<>(null, "Canceled!", true));
    }
    @PreAuthorize(PreAuthUtil.hasDELIVER)
    @GetMapping("/deliver/getAll/{location_code}")
    public  ResponseEntity<ResponseLayout<List<OrderEntity>>> getAllOrderForDeliver(@PathVariable("location_code") String location_code){
        List<OrderEntity> list = orderService.getOrdersForDeliver(location_code);
        return ResponseEntity.ok(new ResponseLayout<>(list, "Got!", true));
    }

}

package com.ron.FoodDelivery.controllers;

import com.ron.FoodDelivery.entities.user.dto.ResponseTotalUserDto;
import com.ron.FoodDelivery.services.UserService;
import com.ron.FoodDelivery.utils.ResponseLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;
    @GetMapping("/user/total")
    public ResponseEntity<ResponseLayout<ResponseTotalUserDto>> getTotalUser() {
        return ResponseEntity.ok(new ResponseLayout<>(userService.totalsUser(), "Got!", true));
    }
}

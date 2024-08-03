package com.ron.FoodDelivery.controllers;

import com.ron.FoodDelivery.services.LikeService;
import com.ron.FoodDelivery.utils.PreAuthUtil;
import com.ron.FoodDelivery.utils.ResponseLayout;
import com.ron.FoodDelivery.utils.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/foods/likes")
public class LikeController {
    @Autowired
    private LikeService likeService;

    @GetMapping("/{food_id}")
    public ResponseEntity<ResponseLayout<Object>> get(@PathVariable("food_id") Long food_id) {
        int total = likeService.totalLike(food_id);
        return ResponseEntity.ok(new ResponseLayout<>(total, "Total!", true));
    }

    @PostMapping("/{food_id}")
    @PreAuthorize(PreAuthUtil.hasUSER)
    public ResponseEntity<ResponseLayout<Object>> like(@PathVariable("food_id") Long food_id) {
        Authentication authentication = SecurityUtil.authentication();
        likeService.like(authentication.getName(), food_id);
        return ResponseEntity.ok(new ResponseLayout<>(null, "Liked!", true));
    }

    @DeleteMapping("/{food_id}")
    @PreAuthorize(PreAuthUtil.hasUSER)
    public ResponseEntity<ResponseLayout<Object>> unlike(@PathVariable("food_id") Long food_id) {
        Authentication authentication = SecurityUtil.authentication();
        likeService.unlike(authentication.getName(), food_id);
        return ResponseEntity.ok(new ResponseLayout<>(null, "Unliked!", true));
    }
}

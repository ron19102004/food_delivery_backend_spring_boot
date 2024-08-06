package com.ron.FoodDelivery.controllers;

import com.ron.FoodDelivery.entities.food.FoodEntity;
import com.ron.FoodDelivery.entities.food.dto.RequestCreateFoodDto;
import com.ron.FoodDelivery.entities.food.dto.RequestUpdateFoodDto;
import com.ron.FoodDelivery.entities.food.dto.ResponseDetailsFoodDto;
import com.ron.FoodDelivery.services.FoodService;
import com.ron.FoodDelivery.utils.PreAuthUtil;
import com.ron.FoodDelivery.utils.ResponseLayout;
import com.ron.FoodDelivery.utils.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/foods")
public class FoodController {
    @Autowired
    private FoodService foodService;

    @GetMapping("/details/{food_id}")
    public ResponseEntity<ResponseLayout<ResponseDetailsFoodDto>> getDetailsFood(@PathVariable("food_id") Long food_id) {
        return ResponseEntity.ok(new ResponseLayout<>(foodService.getDetailsFood(food_id), "Got!", true));
    }

    @PreAuthorize(PreAuthUtil.hasSELLER)
    @PostMapping("/new")
    public ResponseEntity<ResponseLayout<FoodEntity>> createFood(@RequestBody RequestCreateFoodDto requestCreateFoodDto) {
        Authentication authentication = SecurityUtil.authentication();
        FoodEntity food = foodService.create(authentication.getName(), requestCreateFoodDto);
        return ResponseEntity.ok(new ResponseLayout<>(food, "Created!", true));
    }

    @PreAuthorize(PreAuthUtil.hasSELLER)
    @PatchMapping("/{id}/update")
    public ResponseEntity<ResponseLayout<FoodEntity>> updateFood(
            @PathVariable("id") Long id, @RequestBody RequestUpdateFoodDto requestUpdateFoodDto) {
        Authentication authentication = SecurityUtil.authentication();
        FoodEntity food = foodService.update(authentication.getName(), id, requestUpdateFoodDto);
        return ResponseEntity.ok(new ResponseLayout<>(food, "Updated!", true));
    }

    @GetMapping("/category/{category_id}")
    public ResponseEntity<ResponseLayout<List<FoodEntity>>> getByCategoryId(
            @PathVariable("category_id") Long category_id,
            @RequestParam(value = "page", required = false, defaultValue = "0") int pageNumber
    ) {
        Page<FoodEntity> foodEntityPage = foodService.findByCategoryIdWithPage(category_id, pageNumber);
        return ResponseEntity.ok(new ResponseLayout<>(foodEntityPage.getContent(), "Page:" + pageNumber + "/" + foodEntityPage.getTotalPages(), true));
    }

    @GetMapping("/seller/uid/{seller_id}")
    public ResponseEntity<ResponseLayout<List<FoodEntity>>> getBySellerId(
            @PathVariable("seller_id") Long seller_id,
            @RequestParam(value = "page", required = false, defaultValue = "0") int pageNumber
    ) {
        Page<FoodEntity> foodEntityPage = foodService.findBySellerIdWithPage(seller_id, pageNumber);
        return ResponseEntity.ok(new ResponseLayout<>(foodEntityPage.getContent(), "Page:" + pageNumber + "/" + foodEntityPage.getTotalPages(), true));
    }
    @GetMapping("/seller/username/{seller_username}")
    public ResponseEntity<ResponseLayout<List<FoodEntity>>> getBySellerUsername(
            @PathVariable("seller_username") String seller_username,
            @RequestParam(value = "page", required = false, defaultValue = "0") int pageNumber
    ) {
        Page<FoodEntity> foodEntityPage = foodService.findBySellerUsernameWithPage(seller_username, pageNumber);
        return ResponseEntity.ok(new ResponseLayout<>(foodEntityPage.getContent(), "Page:" + pageNumber + "/" + foodEntityPage.getTotalPages(), true));
    }
    @PreAuthorize(PreAuthUtil.hasSELLER)
    @DeleteMapping("/{food_id}")
    public ResponseEntity<ResponseLayout<Object>> deleteById(
            @PathVariable("food_id")Long food_id
    ) {
        Authentication authentication = SecurityUtil.authentication();
        foodService.deleteById(food_id,authentication.getName());
        return ResponseEntity.ok(new ResponseLayout<>(null, "Deleted!", true));
    }
}

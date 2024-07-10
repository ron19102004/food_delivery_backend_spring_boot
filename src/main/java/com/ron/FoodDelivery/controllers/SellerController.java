package com.ron.FoodDelivery.controllers;

import com.ron.FoodDelivery.entities.seller.dto.RequestCreateSellerDto;
import com.ron.FoodDelivery.utils.PreAuthUtil;
import com.ron.FoodDelivery.utils.ResponseLayout;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sellers")
public class SellerController {

    @PostMapping("/update-information")
    @PreAuthorize(PreAuthUtil.hasSELLER)
    public ResponseEntity<ResponseLayout<Object>> update_information(@NotNull @RequestBody RequestCreateSellerDto requestCreateSellerDto) {
        return null;
    }
}

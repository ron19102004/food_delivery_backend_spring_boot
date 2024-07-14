package com.ron.FoodDelivery.controllers;

import com.ron.FoodDelivery.entities.deliver.dto.RequestUpdateInformationDeliveryDto;
import com.ron.FoodDelivery.services.DeliveryService;
import com.ron.FoodDelivery.utils.PreAuthUtil;
import com.ron.FoodDelivery.utils.ResponseLayout;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/deliveries")
public class DeliveryController {
    @Autowired
    private DeliveryService deliveryService;

    @PostMapping("/update-information")
    @PreAuthorize(PreAuthUtil.hasDELIVER)
    public ResponseEntity<ResponseLayout<Object>> update_information(@NotNull @RequestBody RequestUpdateInformationDeliveryDto requestUpdateInformationDeliveryDto) {
        deliveryService.update_information("admin", requestUpdateInformationDeliveryDto);
        return null;
    }
}

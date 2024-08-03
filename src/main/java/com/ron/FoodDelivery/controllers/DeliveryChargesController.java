package com.ron.FoodDelivery.controllers;

import com.ron.FoodDelivery.entities.delivery_charges.DeliveryChargesEntity;
import com.ron.FoodDelivery.entities.delivery_charges.dto.RequestCreateDeliveryChargesDto;
import com.ron.FoodDelivery.services.DeliveryChargesService;
import com.ron.FoodDelivery.utils.PreAuthUtil;
import com.ron.FoodDelivery.utils.ResponseLayout;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/deliveries-charges")
public class DeliveryChargesController {
    @Autowired
    private DeliveryChargesService deliveryChargesService;

    @PostMapping("")
    @PreAuthorize(PreAuthUtil.hasADMIN)
    public ResponseEntity<ResponseLayout<DeliveryChargesEntity>> create(
            @NotNull @RequestBody RequestCreateDeliveryChargesDto requestCreateDeliveryChargesDto) {
        DeliveryChargesEntity deliveryCharges = deliveryChargesService.create(requestCreateDeliveryChargesDto);
        return ResponseEntity.ok(new ResponseLayout<>(deliveryCharges, "Created!", true));
    }

    @PostMapping("/{id}/change-active")
    @PreAuthorize(PreAuthUtil.hasADMIN)
    public ResponseEntity<ResponseLayout<Object>> changeActive(@PathVariable("id") Long id) {
        deliveryChargesService.changeActive(id);
        return ResponseEntity.ok(new ResponseLayout<>(null, "Changed!", true));
    }

    @GetMapping("")
    public ResponseEntity<ResponseLayout<List<DeliveryChargesEntity>>> getAll() {
        return ResponseEntity.ok(new ResponseLayout<>(deliveryChargesService.getAll(), "Found!", true));
    }
}

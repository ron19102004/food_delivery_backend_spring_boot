package com.ron.FoodDelivery.controllers;

import com.ron.FoodDelivery.entities.order.dto.ResponseAllOrderSellerDto;
import com.ron.FoodDelivery.entities.seller.SellerEntity;
import com.ron.FoodDelivery.entities.seller.dto.RequestUpdateInformationSellerDto;
import com.ron.FoodDelivery.services.OrderService;
import com.ron.FoodDelivery.services.SellerService;
import com.ron.FoodDelivery.utils.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/sellers")
public class SellerController {
    @Autowired
    private SellerService sellerService;
    @Autowired
    private OrderService orderService;
    private final ConsoleUtil log = ConsoleUtil.newConsoleLog(this.getClass());

    @PostMapping("/update-information")
    @PreAuthorize(PreAuthUtil.hasSELLER)
    public ResponseEntity<ResponseLayout<Object>> updateInformation(
            @NotNull @RequestBody RequestUpdateInformationSellerDto requestUpdateInformationSellerDto
    ) {
        Authentication authentication = SecurityUtil.authentication();
        sellerService.update_information(authentication.getName(), requestUpdateInformationSellerDto);
        return ResponseEntity.ok(new ResponseLayout<>(null, "Updated!", true));
    }

    @GetMapping("/me")
    @PreAuthorize(PreAuthUtil.hasSELLER)
    public ResponseEntity<ResponseLayout<SellerEntity>> getMyInfo() {
        Authentication authentication = SecurityUtil.authentication();
        return ResponseEntity.ok(new ResponseLayout<>(sellerService.findByUsername(authentication.getName()), "Got!", true));
    }
    @GetMapping("/details/{id}")
    public ResponseEntity<ResponseLayout<SellerEntity>> getMyInfoSeller(@PathVariable("id") Long id) {
        return ResponseEntity.ok(new ResponseLayout<>(sellerService.findById(id), "Got!", true));
    }
    @GetMapping("/orders")
    @PreAuthorize(PreAuthUtil.hasSELLER)
    public ResponseEntity<ResponseLayout<ResponseAllOrderSellerDto>> getOrders() {
        Authentication authentication = SecurityUtil.authentication();
        return ResponseEntity.ok(new ResponseLayout<>(orderService.getOrdersBySellerUsername(authentication.getName()), "Got!!", true));
    }
}

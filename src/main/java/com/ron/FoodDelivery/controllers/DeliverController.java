package com.ron.FoodDelivery.controllers;

import com.ron.FoodDelivery.entities.deliver.dto.RequestUpdateInformationDeliverDto;
import com.ron.FoodDelivery.services.DeliverService;
import com.ron.FoodDelivery.utils.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/deliveries")
public class DeliverController {
    @Autowired
    private DeliverService deliverService;

    @PostMapping("/update-information")
    @PreAuthorize(PreAuthUtil.hasDELIVER)
    public ResponseEntity<ResponseLayout<Object>> update_information(
            @NotNull @RequestBody RequestUpdateInformationDeliverDto requestUpdateInformationDeliverDto
    ) {
        Authentication authentication = SecurityUtil.authentication();
        deliverService.update_information(authentication.getName(), requestUpdateInformationDeliverDto);
        return ResponseEntity.ok(new ResponseLayout<>(null, "Updated!", true));
    }

    @PostMapping("/update-avatar")
    @PreAuthorize(PreAuthUtil.hasDELIVER)
    public ResponseEntity<ResponseLayout<String>> update_avatar(@NotNull @RequestParam("file") MultipartFile file) {
        Authentication authentication = SecurityUtil.authentication();
        LogUtil.log(DeliverController.class, file, LogUtil.Status.INFO);
        return ResponseEntity.ok(new ResponseLayout<>(
                deliverService.update_avatar(authentication.getName(), file),
                "Updated!",
                true));
    }
}

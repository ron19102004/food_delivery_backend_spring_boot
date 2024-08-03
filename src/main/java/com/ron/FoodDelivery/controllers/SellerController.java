package com.ron.FoodDelivery.controllers;

import com.ron.FoodDelivery.entities.seller.dto.RequestUpdateInformationSellerDto;
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

    @PostMapping("/update-avatar")
    @PreAuthorize(PreAuthUtil.hasSELLER)
    public ResponseEntity<ResponseLayout<String>> updateAvatar(@NotNull @RequestParam("file") MultipartFile file) {
        Authentication authentication = SecurityUtil.authentication();
        log.info(file,"Log at func update_avatar");
        return ResponseEntity.ok(new ResponseLayout<>(
                sellerService.update_avatar(authentication.getName(), file),
                "Updated!",
                true));
    }
}

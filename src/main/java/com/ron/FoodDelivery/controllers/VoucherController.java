package com.ron.FoodDelivery.controllers;

import com.ron.FoodDelivery.entities.voucher.VoucherEntity;
import com.ron.FoodDelivery.entities.voucher.dto.RequestCreateVoucherDto;
import com.ron.FoodDelivery.services.VoucherService;
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
@RequestMapping("/vouchers")
public class VoucherController {
    @Autowired
    private VoucherService voucherService;

    @PreAuthorize(PreAuthUtil.hasSELLER)
    @PostMapping("/seller/new")
    public ResponseEntity<ResponseLayout<VoucherEntity>> create(@RequestBody RequestCreateVoucherDto requestCreateVoucherDto) {
        Authentication authentication = SecurityUtil.authentication();
        VoucherEntity voucher = voucherService.create(authentication.getName(), requestCreateVoucherDto);
        return ResponseEntity.ok(new ResponseLayout<>(voucher, "Created!", true));
    }

    @PreAuthorize(PreAuthUtil.hasADMIN)
    @PostMapping("/admin/new")
    public ResponseEntity<ResponseLayout<VoucherEntity>> createWithAdmin(@RequestBody RequestCreateVoucherDto requestCreateVoucherDto) {
        VoucherEntity voucher = voucherService.createWithSystem(requestCreateVoucherDto);
        return ResponseEntity.ok(new ResponseLayout<>(voucher, "Created!", true));
    }

    @PreAuthorize(PreAuthUtil.hasADMIN_SELLER)
    @PatchMapping("/changeHidden/{voucher_id}")
    public ResponseEntity<ResponseLayout<Object>> changeHidden(@PathVariable("voucher_id") Long voucher_id) {
        Authentication authentication = SecurityUtil.authentication();
        voucherService.changeHidden(authentication.getName(), voucher_id);
        return ResponseEntity.ok(new ResponseLayout<>(null, "Changed!", true));
    }
    @GetMapping("/{seller_id}")
    public ResponseEntity<ResponseLayout<List<VoucherEntity>>> getBySellerId(@PathVariable("seller_id") Long seller_id ){
        List<VoucherEntity> voucherEntities = voucherService.findAllBySellerId(seller_id);
        return ResponseEntity.ok(new ResponseLayout<>(voucherEntities, "Got!", true));
    }
}

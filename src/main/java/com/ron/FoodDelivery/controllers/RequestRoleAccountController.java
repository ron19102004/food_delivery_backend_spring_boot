package com.ron.FoodDelivery.controllers;

import com.ron.FoodDelivery.entities.request_role_account.RequestRole;
import com.ron.FoodDelivery.entities.request_role_account.RequestStatus;
import com.ron.FoodDelivery.entities.request_role_account.dto.RequestCreateRequestRoleAccDto;
import com.ron.FoodDelivery.entities.request_role_account.dto.RequestHandleAccountDto;
import com.ron.FoodDelivery.entities.user.UserEntity;
import com.ron.FoodDelivery.services.RequestRoleAccountService;
import com.ron.FoodDelivery.services.UserService;
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
@RequestMapping("/users/request-role")
public class RequestRoleAccountController {
    //services
    @Autowired
    private UserService userService;
    @Autowired
    private RequestRoleAccountService requestRoleAccountService;

    //apis
    @GetMapping("/roles")
    public ResponseEntity<List<RequestRole>> requestRole() {
        return ResponseEntity.ok(List.of(RequestRole.values()));
    }

    @GetMapping("/status")
    public ResponseEntity<List<RequestStatus>> requestStatus() {
        return ResponseEntity.ok(List.of(RequestStatus.values()));
    }

    @PostMapping("/new")
    @PreAuthorize(PreAuthUtil.hasUSER)
    public ResponseEntity<ResponseLayout<Object>> requestRoleAccount(@RequestBody RequestCreateRequestRoleAccDto requestCreateRequestRoleAccDto) {
        Authentication authentication = SecurityUtil.authentication();
        UserEntity user = userService.findByUsername(authentication.getName());
        return ResponseEntity.ok(requestRoleAccountService.createRequestRoleAcc(user, requestCreateRequestRoleAccDto));
    }

    @PostMapping("/cancel")
    @PreAuthorize(PreAuthUtil.hasDELIVER_SELLER)
    public ResponseEntity<ResponseLayout<Object>> cancelRoleAccount() {
        Authentication authentication = SecurityUtil.authentication();
        UserEntity user = userService.findByUsername(authentication.getName());
        requestRoleAccountService.cancelAccount(user);
        return ResponseEntity.ok(new ResponseLayout<>(null, "Canceled!", true));
    }

    @PostMapping("/{id}/handle")
    public ResponseEntity<ResponseLayout<Object>> handleRoleAccount(@PathVariable("id") Long id, @RequestBody RequestHandleAccountDto requestHandleAccountDto) {
        requestRoleAccountService.handle(id, requestHandleAccountDto.is_accepted());
        return ResponseEntity.ok(new ResponseLayout<>(null, "Handled!", true));
    }
}

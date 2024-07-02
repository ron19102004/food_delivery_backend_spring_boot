package com.ron.FoodDelivery.auth;

import com.ron.FoodDelivery.auth.dto.RequestLoginDto;
import com.ron.FoodDelivery.auth.dto.RequestRegisterDto;
import com.ron.FoodDelivery.auth.dto.RequestVerifyOTPDto;
import com.ron.FoodDelivery.entities.token.UserAgent;
import com.ron.FoodDelivery.utils.PreAuthUtil;
import com.ron.FoodDelivery.utils.ResponseLayout;
import com.ron.FoodDelivery.utils.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ResponseLayout> login(@RequestBody RequestLoginDto requestLoginDto, HttpServletRequest request) {
        String userAgentRequest = request.getHeader("User-Agent");
        UserAgent user_agent = UserAgent.MOBILE;
        if (!userAgentRequest.equals("mobile")) {
            user_agent = UserAgent.WEB;
        }
        return ResponseEntity.ok(authService.login(requestLoginDto, user_agent));
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseLayout> register(@RequestBody RequestRegisterDto requestRegisterDto) {
        return ResponseEntity.ok(authService.register(requestRegisterDto));
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<ResponseLayout> verifyOtp(@RequestBody RequestVerifyOTPDto requestVerifyOTPDto, HttpServletRequest request) {
        String userAgentRequest = request.getHeader("User-Agent");
        UserAgent user_agent = UserAgent.MOBILE;
        if (!userAgentRequest.equals("mobile")) {
            user_agent = UserAgent.WEB;
        }
        return ResponseEntity.ok(authService.verify_otp(requestVerifyOTPDto,user_agent));
    }

    @PostMapping("/change-tfa")
    @PreAuthorize(PreAuthUtil.hasAll)
    public ResponseEntity<ResponseLayout> changeTfa() {
        Authentication authentication = SecurityUtil.authentication();
        return ResponseEntity.ok(authService.change_tfa(authentication.getName()));
    }
}

package com.ron.FoodDelivery;

import com.ron.FoodDelivery.auth.dto.RequestRegisterDto;
import com.ron.FoodDelivery.entities.user.UserRole;
import com.ron.FoodDelivery.services.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class FoodDeliveryApplication {
    @Autowired
    private UserService userService;

    @PostConstruct
    private void initializer() {
        userService.save(UserRole.ADMIN, RequestRegisterDto.builder()
                .email("ron19102004@gmail.com")
                .first_name("Admin")
                .last_name("Admin")
                .phone_number("0392477615")
                .username("admin")
                .password("admin")
                .build());
    }

    public static void main(String[] args) {
        SpringApplication.run(FoodDeliveryApplication.class, args);
    }
}

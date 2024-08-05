package com.ron.FoodDelivery;

import com.ron.FoodDelivery.auth.dto.RequestRegisterDto;
import com.ron.FoodDelivery.entities.user.UserRole;
import com.ron.FoodDelivery.services.UserService;
import com.ron.FoodDelivery.utils.ConsoleUtil;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FoodDeliveryApplication {
    private final ConsoleUtil log = ConsoleUtil.newConsoleLog(this.getClass());
    @Value("${server.port}")
    private int PORT;
    @Autowired
    private UserService userService;

    @PostConstruct
    private void initialize() {
        log.info("SwaggerURL: http://localhost:" + PORT + "/swagger-ui/index.html", "Log at func initialize");
        userService.save(UserRole.ADMIN, RequestRegisterDto.builder()
                .email("ron19102004@gmail.com")
                .first_name("Admin")
                .last_name("Admin")
                .phone_number("0392477615")
                .username("admin")
                .password("admin12345")
                .build());
    }

    public static void main(String[] args) {
        SpringApplication.run(FoodDeliveryApplication.class, args);
    }
}

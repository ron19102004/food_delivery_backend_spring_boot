package com.ron.FoodDelivery.auth.dto;

import com.ron.FoodDelivery.configurations.ApplicationConfiguration;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;

@Builder
public record RequestRegisterDto(
        @NotNull String first_name,
        @NotNull String last_name,
        @NotNull @Length(min = 5) String username,
        @NotNull @Pattern(regexp = ApplicationConfiguration.EMAIL_REGEX) String email,
        @NotNull @Length(max = 11) @Pattern(regexp = ApplicationConfiguration.TEL_REGEX) String phone_number,
        @NotNull String password
) {
}

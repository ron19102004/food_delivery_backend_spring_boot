package com.ron.FoodDelivery.auth.dto;

import com.ron.FoodDelivery.utils.RegexValid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public record RequestRegisterDto(
        @NotNull String first_name,
        @NotNull String last_name,
        @NotNull @Length(min = 5) String username,
        @NotNull @Pattern(regexp = RegexValid.EMAIL) String email,
        @NotNull @Length(max = 11) @Pattern(regexp = RegexValid.TEL) String phone_number,
        @NotNull String password
) {
}

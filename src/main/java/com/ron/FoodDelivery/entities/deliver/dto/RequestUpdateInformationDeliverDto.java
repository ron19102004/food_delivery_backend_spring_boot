package com.ron.FoodDelivery.entities.deliver.dto;

import com.ron.FoodDelivery.configurations.ApplicationConfiguration;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public record RequestUpdateInformationDeliverDto(
        @NotNull @Pattern(regexp = ApplicationConfiguration.EMAIL_REGEX) String email,
        @NotNull @Pattern(regexp = ApplicationConfiguration.TEL_REGEX) String phone_number,
        @NotNull @Length(min = 5) String name
) {
}

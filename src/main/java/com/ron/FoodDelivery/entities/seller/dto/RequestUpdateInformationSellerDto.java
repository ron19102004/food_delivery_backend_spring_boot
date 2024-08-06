package com.ron.FoodDelivery.entities.seller.dto;

import com.ron.FoodDelivery.configurations.ApplicationConfiguration;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

public record RequestUpdateInformationSellerDto(
        @NotNull @Length(min = 5) String name,
        @NotNull String address,
        @NotNull Double latitude,
        @NotNull Double longitude,
        @NotNull @Pattern(regexp = ApplicationConfiguration.TEL_REGEX) String phone_number,
        @NotNull @Pattern(regexp = ApplicationConfiguration.EMAIL_REGEX) String email,
        @NotNull Date open_at,
        @NotNull Date close_at,
        @NotNull Long location_id,
        @NotNull String avatar_url,
        @NotNull String background_url
) {
}

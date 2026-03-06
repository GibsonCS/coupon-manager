package com.onebrain.onebrain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CouponRequest(
        @NotNull()
        @Size(min = 6)
        String code,

        @NotBlank(message = "Description is required")
        String description,

        @NotNull
        Double discountValue,

        @NotNull
        String expirationDate,


        boolean published
) {
}


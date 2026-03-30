package com.onebrain.onebrain.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.Instant;

public record CouponRequest(
        @NotNull()
        @Size(min = 6)
        String code,

        @NotBlank(message = "Description is required")
        String description,

        @NotNull
        BigDecimal discountValue,

        @NotNull
        @Future(message = "The date must be in future")
        Instant expirationDate,

        boolean published
) {
}


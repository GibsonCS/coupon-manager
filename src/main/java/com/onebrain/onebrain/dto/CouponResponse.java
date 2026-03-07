package com.onebrain.onebrain.dto;

import com.onebrain.onebrain.domain.CouponStatus;

import java.math.BigDecimal;
import java.util.UUID;

public record CouponResponse(
        UUID id,
        String code,
        String description,
        BigDecimal discountValue,
        String expirationDate,
        CouponStatus status,
        boolean published,
        boolean redeemed) {
}

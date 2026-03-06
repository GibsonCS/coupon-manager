package com.onebrain.onebrain.domain;

import com.onebrain.onebrain.exception.BusinessException;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class Coupon {
    private final String code;
    private final String description;
    private final BigDecimal discountValue;
    private final LocalDate expirationDate;
    private final CouponStatus status;
    private final boolean published;
    private final boolean redeemed;

    private boolean deleted;

    private Coupon(String code, String description, BigDecimal discountValue, LocalDate expirationDate, boolean published) {
        this.code = code;
        this.description = description;
        this.discountValue = discountValue;
        this.expirationDate = expirationDate;
        this.status = CouponStatus.ACTIVE;
        this.published = published;
        this.redeemed = false;
        this.deleted = false;
    }

    public static Coupon create(String code, String description, BigDecimal discountValue, String expirationDate, boolean published) {
        validateExpirationDate(expirationDate);
        validateDiscount(discountValue);
        String sanitizedCode = sanitizeCode(code);

        return new Coupon(sanitizedCode, description, discountValue, Instant.parse(expirationDate).atZone(ZoneId.of("UTC")).toLocalDate(), published);
    }

    private static String sanitizeCode(String couponCode) {
        if (couponCode.length() < 6) throw new BusinessException("Invalid coupon code length");
        String sanitizedCoupon = couponCode.replaceAll("[^a-zA-Z0-9]$", "");
        if (sanitizedCoupon.length() != 6)
            throw new BusinessException("Invalid coupon code length after remove special characters");

        return sanitizedCoupon;
    }

    private static void validateExpirationDate(String expirationDate) {
        LocalDate localDate = Instant.parse(expirationDate).atZone(ZoneId.of("UTC")).toLocalDate();

        if (localDate.isBefore(LocalDate.now())) {
            throw new BusinessException("Invalid coupon expiration date");
        }
    }

    private static void validateDiscount(BigDecimal couponDiscount) {
        if (couponDiscount.compareTo(new BigDecimal("0.5")) < 0) {
            throw new BusinessException("The min discount value is 0.5");
        }
    }

    public void delete() {
        if (this.deleted) {
            throw new BusinessException("Coupon already deleted");
        }
        this.deleted = true;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getDiscountValue() {
        return discountValue;
    }

    public String getExpirationDate() {
        return expirationDate.toString();
    }

    public CouponStatus getCouponStatus() {
        return status;
    }

    public boolean getPublished() {
        return published;
    }

    public boolean getRedeemed() {
        return redeemed;
    }

    public CouponStatus getStatus() {
        return status;
    }

    public boolean isPublished() {
        return published;
    }

    public boolean isRedeemed() {
        return redeemed;
    }

    public boolean isDeleted() {
        return deleted;
    }
}

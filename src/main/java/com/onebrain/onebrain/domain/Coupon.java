package com.onebrain.onebrain.domain;

import com.onebrain.onebrain.exception.BusinessException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Coupon {
    private String code;
    private String description;
    private BigDecimal discountValue;
    private LocalDate expirationDate;

    private CouponStatus couponStatus = CouponStatus.ACTIVE;
    private boolean published = false;
    private boolean redeemed = false;

    private Coupon(String code, String description, BigDecimal discountValue, LocalDate expirationDate) {
        this.code = code;
        this.description = description;
        this.discountValue = discountValue;
        this.expirationDate = expirationDate;
    }

    public static Coupon create(String code, String description, BigDecimal discountValue, String expirationDate) {
        validateExpirationDate(expirationDate);
        validateDiscount(discountValue);
        String sanitizedCode = sanitizeCode(code);

        return new Coupon(sanitizedCode, description, discountValue, LocalDate.parse(expirationDate));
    }

    private static String sanitizeCode(String couponCode) {
        if (couponCode.length() != 6) throw new BusinessException("Invalid coupon code length");
        String sanitizedCoupon = couponCode.replaceAll("[^a-zA-Z0-9]$", "");
        if (sanitizedCoupon.length() != 6)
            throw new BusinessException("Invalid coupon code length after sanitize coupon");

        return sanitizedCoupon;
    }

    private static void validateExpirationDate(String expirationDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        LocalDate expirationDateFormated = LocalDate.parse(expirationDate, formatter);

        if (expirationDateFormated.isBefore(LocalDate.now())) {
            throw new BusinessException("Invalid coupon expiration date");
        }
    }

    private static void validateDiscount(BigDecimal couponDiscount) {
        if (couponDiscount.compareTo(new BigDecimal("0.5")) < 0) {
            throw new BusinessException("Min discount value is 0.5");
        }
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
        return couponStatus;
    }

    public boolean getPublished() {
        return published;
    }

    public boolean getRedeemed() {
        return redeemed;
    }
}

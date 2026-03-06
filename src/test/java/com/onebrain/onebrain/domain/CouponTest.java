package com.onebrain.onebrain.domain;

import com.onebrain.onebrain.exception.BusinessException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class CouponTest {

    @Test
    void shouldThrowExceptionWhenCouponCodeIsLessThanSix() {
        BusinessException exception = assertThrows(BusinessException.class, () -> Coupon.create(
                "123",
                "Description",
                new BigDecimal("0.5"),
                "2026-11-04T17:14:45.180Z",
                false)
        );

        String expectedMessage = "Invalid coupon code length";

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenCouponExpirationDateIsInvalid() {
        BusinessException exception = assertThrows(BusinessException.class, () -> Coupon.create(
                "123SDF",
                "Description",
                new BigDecimal("0.5"),
                "2026-02-04T17:14:45.180Z",
                false)
        );

        String expectedMessage = "Invalid coupon expiration date";

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionAfterRemoveSpecialCharacters() {
        BusinessException exception = assertThrows(BusinessException.class, () -> Coupon.create(
                "A123SADA$%#",
                "Description",
                new BigDecimal("0.5"),
                "2026-11-04T17:14:45.180Z",
                false)
        );

        String expectedMessage = "Invalid coupon code length after remove special characters";

        assertEquals(expectedMessage, exception.getMessage());
    }


    @Test
    void shouldThrowExceptionAfterWhenInvalidDiscountValue() {
        BusinessException exception = assertThrows(BusinessException.class, () -> Coupon.create(
                "A123SA",
                "Description",
                new BigDecimal("0.3"),
                "2026-11-04T17:14:45.180Z",
                false)
        );

        String expectedMessage = "The min discount value is 0.5";

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void shouldCreateNewCoupon() {
        Coupon coupon = Coupon.create(
                "A123SA",
                "Description",
                new BigDecimal("0.6"),
                "2026-11-04T17:14:45.180Z",
                false
        );
        assertEquals("A123SA", coupon.getCode());
        assertEquals(new BigDecimal("0.6"), coupon.getDiscountValue());
    }

    @Test
    void shouldThrowExceptionIfCouponAlreadyDeleted() {
        Coupon coupon = Coupon.create(
                "A123SA",
                "Description",
                new BigDecimal("0.6"),
                "2026-11-04T17:14:45.180Z",
                false
        );

        coupon.delete();

        BusinessException exception = assertThrows(BusinessException.class, coupon::delete);

        String expectedMessage = "Coupon already deleted";

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void shouldDeleteCoupon() {
        Coupon coupon = Coupon.create(
                "A123SA",
                "Description",
                new BigDecimal("0.6"),
                "2026-11-04T17:14:45.180Z",
                false
        );

        coupon.delete();

        assertTrue(coupon.isDeleted());
    }
}

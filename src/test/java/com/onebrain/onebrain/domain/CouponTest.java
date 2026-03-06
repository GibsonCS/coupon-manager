package com.onebrain.onebrain.domain;


import com.onebrain.onebrain.dto.CouponDTO;
import com.onebrain.onebrain.exception.BusinessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class CouponTest {

    @Test
    void shouldThrowExceptionWhenCouponCodeIsLessThanSix() {
       BusinessException exception =  assertThrows(BusinessException.class, () -> Coupon.create(
                "123",
                "Description",
                new BigDecimal("0.5"),
               "20/04/2026")
       );

       String expectedMessage = "Invalid coupon code length";

       assertEquals(exception.getMessage(), expectedMessage);
    }

    @Test
    void shouldThrowExceptionWhenCouponExpirationDateIsInvalid() {

        BusinessException exception =  assertThrows(BusinessException.class, () -> Coupon.create(
                "123SDF",
                "Description",
                new BigDecimal("0.5"),
                "04/03/2026")
        );

        String expectedMessage = "Invalid coupon expiration date";

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionAfterRemoveSpecialCharacters() {
        BusinessException exception =  assertThrows(BusinessException.class, () -> Coupon.create(
                "A123SADA$%#",
                "Description",
                new BigDecimal("0.5"),
                "07/03/2026")
        );

        String expectedMessage = "Invalid coupon code length after remove special characters";

        assertEquals(expectedMessage, exception.getMessage());
    }


    @Test
    void shouldThrowExceptionAfterWhenInvalidDiscountValue() {
        BusinessException exception =  assertThrows(BusinessException.class, () -> Coupon.create(
                "A123SA",
                "Description",
                new BigDecimal("0.3"),
                "07/03/2026")
        );

        String expectedMessage = "The min discount value is 0.5";

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void shouldCreateNewCoupon() {
        BusinessException exception =  assertThrows(BusinessException.class, () -> Coupon.create(
                "A123SA",
                "Description",
                new BigDecimal("0.3"),
                "07/03/2026")
        );

        String expectedMessage = "The min discount value is 0.5";

        assertEquals(expectedMessage, exception.getMessage());
    }




}

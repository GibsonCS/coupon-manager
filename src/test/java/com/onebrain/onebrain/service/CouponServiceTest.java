package com.onebrain.onebrain.service;

import com.onebrain.onebrain.domain.Coupon;
import com.onebrain.onebrain.dto.CouponRequest;
import com.onebrain.onebrain.dto.CouponResponse;
import com.onebrain.onebrain.exception.BusinessException;
import com.onebrain.onebrain.repository.CouponRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CouponServiceTest {

    @Mock
    CouponRepository couponRepository;

    @InjectMocks
    CouponService couponService;

    CouponRequest couponRequestMock;

    Coupon mockCoupon;

    @Test
    void shouldThrowAnExceptionIfCodeAlreadyExists() {
        mockCoupon = Coupon.create(
                "ACDEA3",
                "",
                new BigDecimal("0.5"),
                Instant.parse("2026-11-04T17:14:45.180Z"),
                false
        );

        couponRequestMock = new CouponRequest(
                "ACDEA3",
                "TEST OF COUPON CREATE",
                new BigDecimal("0.5"),
                Instant.parse("2026-11-04T17:14:45.180Z"),
                false
        );

        Mockito
                .when(couponRepository.findByCode(couponRequestMock.code()))
                .thenReturn(Optional.of(mockCoupon));

        assertThrows(BusinessException.class, () -> couponService.create(couponRequestMock));

        Mockito.verify(couponRepository).findByCode(Mockito.anyString());
    }

    @Test
    void shouldCreateNewCoupon() {
        mockCoupon = Coupon.create(
                "ACDEA4",
                "TEST OF COUPON CREATE",
                new BigDecimal("0.5"),
                Instant.parse("2026-11-04T17:14:45.180Z"),
                false
        );

        couponRequestMock = new CouponRequest(
                "ACDEA4",
                "TEST OF COUPON CREATE",
                new BigDecimal("0.5"),
                Instant.parse("2026-11-04T17:14:45.180Z"),
                false
        );

        Mockito
                .when(couponRepository.findByCode(Mockito.anyString()))
                .thenReturn(Optional.empty());

        Mockito
                .when(couponRepository.save(Mockito.any()))
                .thenReturn(mockCoupon);

        CouponResponse couponResponse = couponService.create(couponRequestMock);

        assertNotNull(couponResponse);
        assertEquals("ACDEA4", couponResponse.code());
        assertEquals("2026-11-04T17:14:45.180Z", couponResponse.expirationDate());
        assertEquals(new BigDecimal("0.5"), couponResponse.discountValue());
        Mockito.verify(couponRepository).save(Mockito.any());
    }

    @Test
    void shouldThrowAnExceptionIfCouponNotExists() {
        UUID couponId = UUID.randomUUID();

        Mockito
                .when(couponRepository.findById(couponId))
                .thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> couponService.get(couponId.toString()));
        Mockito.verify(couponRepository).findById(couponId);
    }

    @Test
    void shouldReturnAnCoupon() {
        String couponId = "73ae73d6-5ca2-4eaf-a93c-ce70bf3649d2";

        mockCoupon = Coupon.create(
                "ACDEA4",
                "TEST OF COUPON CREATE",
                new BigDecimal("0.5"),
                Instant.parse("2026-11-04T17:14:45.180Z"),
                false
        );

        Mockito
                .when(couponRepository.findById(UUID.fromString(couponId)))
                .thenReturn(Optional.of(mockCoupon));

        CouponResponse couponResponse = couponService.get(couponId);

        assertEquals("ACDEA4", couponResponse.code());
        assertEquals("2026-11-04T17:14:45.180Z", couponResponse.expirationDate());
        assertEquals(new BigDecimal("0.5"), couponResponse.discountValue());
        Mockito.verify(couponRepository).findById(UUID.fromString(couponId));
    }

    @Test
    void shouldThrowAnExceptionIfCouponHasAlreadyBennDeleted() {
        UUID couponId = UUID.randomUUID();

        mockCoupon = Coupon.create(
                "ACDEA4",
                "TEST OF COUPON CREATE",
                new BigDecimal("0.5"),
                Instant.parse("2026-11-04T17:14:45.180Z"),
                false
        );

        mockCoupon.delete();

        Mockito
                .when(couponRepository.findById(couponId))
                .thenReturn(Optional.of(mockCoupon));

        assertThrows(BusinessException.class, () -> couponService.delete(couponId.toString()));
        Mockito.verify(couponRepository).findById(couponId);
    }

    @Test
    void shouldDeleteACoupon() {
        UUID couponIdUUID = UUID.randomUUID();

        mockCoupon = Coupon.create(
                "ACDEA4",
                "TEST OF COUPON CREATE",
                new BigDecimal("0.5"),
                Instant.parse("2026-11-04T17:14:45.180Z"),
                false
        );

        Mockito
                .when(couponRepository.findById(couponIdUUID))
                .thenReturn(Optional.of(mockCoupon));

        couponService.delete(couponIdUUID.toString());

        Mockito.verify(couponRepository).findById(couponIdUUID);
    }
}
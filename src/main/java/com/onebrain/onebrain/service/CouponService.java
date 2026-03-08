package com.onebrain.onebrain.service;

import com.onebrain.onebrain.domain.Coupon;
import com.onebrain.onebrain.dto.CouponRequest;
import com.onebrain.onebrain.dto.CouponResponse;
import com.onebrain.onebrain.exception.BusinessException;
import com.onebrain.onebrain.repository.CouponRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CouponService {

    private final CouponRepository couponRepository;

    public CouponService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    public CouponResponse create(CouponRequest couponRequest) {

        couponRepository.findByCode(couponRequest.code()).ifPresent(c -> {
            throw new BusinessException("Coupon code already exists");
        });

        Coupon coupon = Coupon.create(
                couponRequest.code(),
                couponRequest.description(),
                couponRequest.discountValue(),
                couponRequest.expirationDate(),
                couponRequest.published()
        );

        Coupon savedCoupon = couponRepository.save(coupon);

        return new CouponResponse(
                savedCoupon.getId(),
                savedCoupon.getCode(),
                savedCoupon.getDescription(),
                savedCoupon.getDiscountValue(),
                savedCoupon.getExpirationDate(),
                savedCoupon.getStatus(),
                savedCoupon.getPublished(),
                savedCoupon.getRedeemed()
        );
    }

    public CouponResponse get(String couponId) {

        UUID couponIdUDDI = UUID.fromString(couponId);
        
        Coupon couponFounded = couponRepository.findById(couponIdUDDI).orElseThrow(() ->
                new BusinessException("Coupon not exists"));

        return new CouponResponse(
                couponFounded.getId(),
                couponFounded.getCode(),
                couponFounded.getDescription(),
                couponFounded.getDiscountValue(),
                couponFounded.getExpirationDate(),
                couponFounded.getStatus(),
                couponFounded.getPublished(),
                couponFounded.getRedeemed()
        );
    }

    public void delete(String couponId) {
        UUID couponIdUUID = UUID.fromString(couponId);

        couponRepository.findById(couponIdUUID).ifPresentOrElse(c -> {
                    c.delete();
                    couponRepository.save(c);
                }, () -> {
                    throw new BusinessException("Coupon not exists");
                }
        );
    }
}

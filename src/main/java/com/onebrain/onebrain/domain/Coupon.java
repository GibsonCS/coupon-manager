package com.onebrain.onebrain.domain.coupon;

import com.onebrain.onebrain.dto.CouponDTO;
import com.onebrain.onebrain.exception.BusinessException;

public class Coupon {
    private String code;
    private String description;
    private Double discountValue;
    private String expirationDate;
    private CouponStatus couponStatus = CouponStatus.ACTIVE;
    private Boolean published = false;
    private Boolean redeemed = false;

    public Coupon(CouponDTO couponDTO) {

    }
}

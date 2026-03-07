package com.onebrain.onebrain.controller;

import com.onebrain.onebrain.dto.CouponRequest;
import com.onebrain.onebrain.dto.CouponResponse;
import com.onebrain.onebrain.service.CouponService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/coupons")
public class CouponController {

    private final CouponService couponService;

    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    @PostMapping
    public ResponseEntity<CouponResponse> create(@Valid @RequestBody CouponRequest couponRequest) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(couponService.create(couponRequest));
    }
}

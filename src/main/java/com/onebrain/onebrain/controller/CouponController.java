package com.onebrain.onebrain.controller;

import com.onebrain.onebrain.dto.CouponRequest;
import com.onebrain.onebrain.dto.CouponResponse;
import com.onebrain.onebrain.service.CouponService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public ResponseEntity<CouponResponse> get(@PathVariable String id) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(couponService.get(id));
    }
}

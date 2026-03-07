package com.onebrain.onebrain.repository;

import com.onebrain.onebrain.domain.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CouponRepository extends JpaRepository<Coupon, UUID> {
    public Optional<Coupon> findByCode(String code);
}

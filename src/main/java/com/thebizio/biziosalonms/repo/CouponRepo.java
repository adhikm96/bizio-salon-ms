package com.thebizio.biziosalonms.repo;

import com.thebizio.biziosalonms.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CouponRepo extends JpaRepository<Coupon, UUID> {
}

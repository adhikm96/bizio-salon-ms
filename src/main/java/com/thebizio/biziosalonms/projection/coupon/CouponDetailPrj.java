package com.thebizio.biziosalonms.projection.coupon;

import com.thebizio.biziosalonms.enums.CouponTypeEnum;
import com.thebizio.biziosalonms.enums.StatusEnum;

import java.time.LocalDateTime;
import java.util.UUID;

public interface CouponDetailPrj {

    UUID getId();
    String getName();
    float getValue();
    LocalDateTime getStartDate();
    LocalDateTime getEndDate();
    Integer getMaxRedemptions();
    CouponTypeEnum getType();
    StatusEnum getStatus();
}

package com.thebizio.biziosalonms.projection.coupon;

import com.thebizio.biziosalonms.enums.CouponTypeEnum;
import com.thebizio.biziosalonms.enums.StatusEnum;

import java.time.LocalDateTime;
import java.util.UUID;

public interface CouponListPrj {

    UUID getId();
    String getName();
    CouponTypeEnum getType();
    float getValue();
    LocalDateTime getStartDate();
    LocalDateTime getEndDate();
    Integer getMaxRedemptions();
    StatusEnum getStatus();
}

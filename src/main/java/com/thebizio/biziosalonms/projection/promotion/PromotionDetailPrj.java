package com.thebizio.biziosalonms.projection.promotion;

import com.thebizio.biziosalonms.enums.StatusEnum;

import java.time.LocalDateTime;
import java.util.UUID;

public interface PromotionDetailPrj {

    UUID getId();
    String getCode();
    LocalDateTime getEndDate();
    Integer getMaxRedemptions();
    Integer getTimesRedeemed();
    StatusEnum getStatus();

}

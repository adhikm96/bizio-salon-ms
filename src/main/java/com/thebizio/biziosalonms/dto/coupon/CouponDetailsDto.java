package com.thebizio.biziosalonms.dto.coupon;

import com.thebizio.biziosalonms.enums.CouponTypeEnum;
import com.thebizio.biziosalonms.enums.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CouponDetailsDto {

    private UUID id;
    private String name;
    private float value;

    private CouponTypeEnum type;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer maxRedemptions;
    private StatusEnum status;
}

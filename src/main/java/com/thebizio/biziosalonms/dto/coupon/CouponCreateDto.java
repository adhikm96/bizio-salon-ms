package com.thebizio.biziosalonms.dto.coupon;

import com.thebizio.biziosalonms.enums.CouponTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CouponCreateDto {

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @Positive
    private float value;

    @NotNull
    private LocalDateTime startDate;

    @NotNull
    private LocalDateTime endDate;

    @NotNull
    @Positive
    private Integer maxRedemptions;

    @NotNull
    CouponTypeEnum couponType;
}

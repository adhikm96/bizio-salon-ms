package com.thebizio.biziosalonms.dto.promotion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromotionCreateDto {

    private String code;
    private LocalDateTime endDate;

    @NotNull
    @Positive
    private Integer maxRedemptions;

    @NotNull
    private UUID couponId;
}

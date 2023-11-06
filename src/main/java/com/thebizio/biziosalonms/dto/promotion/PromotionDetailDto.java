package com.thebizio.biziosalonms.dto.promotion;

import com.thebizio.biziosalonms.enums.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromotionDetailDto {

    private UUID id;
    private String code;
    private LocalDateTime endDate;
    private Integer maxRedemptions;
    private Integer timesRedeemed;
    private StatusEnum status;
}

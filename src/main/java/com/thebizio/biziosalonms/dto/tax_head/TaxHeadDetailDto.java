package com.thebizio.biziosalonms.dto.tax_head;

import com.thebizio.biziosalonms.enums.ChargeOnEnum;
import com.thebizio.biziosalonms.enums.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.util.UUID;

@Data@AllArgsConstructor@NoArgsConstructor
public class TaxHeadDetailDto {

    private UUID id;
    private String name;
    private String code;
    private ChargeOnEnum chargeOn;
    private StatusEnum status;
}

package com.thebizio.biziosalonms.dto.tax_schedule_item;

import com.thebizio.biziosalonms.enums.TaxChargeTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.UUID;

@Data@AllArgsConstructor@NoArgsConstructor
public class CreateUpdateTaxScheduleItemDto {

    @NotNull
    private UUID taxHeadId;

    @NotNull
    private TaxChargeTypeEnum taxChargeType;

    @NotNull@Positive
    private float value;

    private float onValueFrom = 0;
    private float onValueTo = 0;

}

package com.thebizio.biziosalonms.dto.tax_schedule_item;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.thebizio.biziosalonms.dto.tax_head.TaxHeadDetailDto;
import com.thebizio.biziosalonms.entity.Branch;
import com.thebizio.biziosalonms.entity.TaxHead;
import com.thebizio.biziosalonms.entity.TaxSchedule;
import com.thebizio.biziosalonms.enums.TaxChargeTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Data@AllArgsConstructor@NoArgsConstructor
public class TaxScheduleItemDetailDto {

    private UUID id;
    private TaxChargeTypeEnum taxChargeType;
    private float value;
    private float onValueFrom;
    private float onValueTo;
    private TaxHeadDetailDto taxHead;
}

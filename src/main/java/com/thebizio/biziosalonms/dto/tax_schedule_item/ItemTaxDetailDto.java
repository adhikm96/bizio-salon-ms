package com.thebizio.biziosalonms.dto.tax_schedule_item;

import com.thebizio.biziosalonms.enums.TaxChargeTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data@AllArgsConstructor@NoArgsConstructor
@EqualsAndHashCode
public class ItemTaxDetailDto {

    private String itemName;
    private String itemCode;
    private String taxName;
    private String taxCode;
    private Double cost;
    private Double price;
    private TaxChargeTypeEnum taxChargeType;
    private Double tax = 0.0;
    private Double taxCalculated = 0.0;
}

package com.thebizio.biziosalonms.dto.checkout;

import com.thebizio.biziosalonms.enums.TaxChargeTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data@AllArgsConstructor@NoArgsConstructor
@EqualsAndHashCode
public class GrossTotalTaxDetailDto {

    private String taxName;
    private String taxCode;
    private Double grossTotal;
    private TaxChargeTypeEnum taxChargeType;
    private Double tax;
    private Double taxCalculated;
}

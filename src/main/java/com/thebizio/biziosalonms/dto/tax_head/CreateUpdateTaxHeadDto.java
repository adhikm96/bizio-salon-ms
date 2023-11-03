package com.thebizio.biziosalonms.dto.tax_head;

import com.thebizio.biziosalonms.enums.ChargeOnEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data@AllArgsConstructor@NoArgsConstructor
public class CreateUpdateTaxHeadDto {

    @NotNull@NotBlank
    private String name;
    @NotNull@NotBlank
    private String code;
    @NotNull
    private ChargeOnEnum chargeOn;
}

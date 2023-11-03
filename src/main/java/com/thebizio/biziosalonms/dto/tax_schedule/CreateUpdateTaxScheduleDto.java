package com.thebizio.biziosalonms.dto.tax_schedule;

import com.thebizio.biziosalonms.entity.Branch;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data@AllArgsConstructor@NoArgsConstructor
public class CreateUpdateTaxScheduleDto {

    @NotNull@NotBlank
    private String name;
    @NotNull
    private UUID branchId;

    @NotEmpty@Valid
    private List<CreateUpdateTaxScheduleItemDto> taxScheduleItems;
}

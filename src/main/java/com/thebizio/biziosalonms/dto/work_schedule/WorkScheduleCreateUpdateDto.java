package com.thebizio.biziosalonms.dto.work_schedule;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
public class WorkScheduleCreateUpdateDto {
    @NotNull @NotBlank
    private String name;

    @NotNull
    @NotEmpty
    @Valid
    List<WsItem> items;
}

package com.thebizio.biziosalonms.dto.work_schedule;

import com.thebizio.biziosalonms.enums.WorkScheduleDayEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WsItem {
    @NotNull
    private WorkScheduleDayEnum day;

    @NotNull
    private LocalTime startTime;

    private LocalTime breakStartTime;
    private LocalTime breakEndTime;

    @NotNull
    private LocalTime endTime;
}

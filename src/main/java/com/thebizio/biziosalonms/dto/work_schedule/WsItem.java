package com.thebizio.biziosalonms.dto.work_schedule;

import com.thebizio.biziosalonms.enums.WorkScheduleDayEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WsItem {
    private WorkScheduleDayEnum day;
    private LocalTime startTime;
    private LocalTime breakStartTime;
    private LocalTime breakEndTime;
    private LocalTime endTime;
}

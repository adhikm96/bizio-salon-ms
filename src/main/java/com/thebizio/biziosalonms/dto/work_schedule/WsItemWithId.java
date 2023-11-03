package com.thebizio.biziosalonms.dto.work_schedule;

import com.thebizio.biziosalonms.enums.WorkScheduleDayEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.UUID;

@Data
@NoArgsConstructor
public class WsItemWithId{
    private UUID id;
    private WorkScheduleDayEnum day;
    private LocalTime startTime;
    private LocalTime breakStartTime;
    private LocalTime breakEndTime;
    private LocalTime endTime;
}

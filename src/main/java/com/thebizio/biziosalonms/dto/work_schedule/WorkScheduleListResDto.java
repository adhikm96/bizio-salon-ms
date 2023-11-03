package com.thebizio.biziosalonms.dto.work_schedule;

import com.thebizio.biziosalonms.enums.StatusEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WorkScheduleListResDto {
    private String name;
    private StatusEnum status;
}

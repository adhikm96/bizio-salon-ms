package com.thebizio.biziosalonms.dto.work_schedule;

import com.thebizio.biziosalonms.enums.StatusEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class WorkScheduleListResDto {
    private String name;
    private UUID id;
    private StatusEnum status;
}

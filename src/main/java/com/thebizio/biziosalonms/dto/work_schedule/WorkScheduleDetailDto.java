package com.thebizio.biziosalonms.dto.work_schedule;

import com.thebizio.biziosalonms.enums.StatusEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class WorkScheduleDetailDto {
    private String name;
    private StatusEnum status;
    private List<WsItemWithId> items;
}

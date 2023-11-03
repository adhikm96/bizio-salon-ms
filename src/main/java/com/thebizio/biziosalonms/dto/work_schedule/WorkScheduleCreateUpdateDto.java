package com.thebizio.biziosalonms.dto.work_schedule;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class WorkScheduleCreateUpdateDto {
    private String name;
    List<WsItem> items;
}

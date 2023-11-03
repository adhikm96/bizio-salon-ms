package com.thebizio.biziosalonms.controller;

import com.thebizio.biziosalonms.dto.ResponseMessageDto;
import com.thebizio.biziosalonms.dto.work_schedule.WorkScheduleCreateUpdateDto;
import com.thebizio.biziosalonms.dto.work_schedule.WorkScheduleDetailDto;
import com.thebizio.biziosalonms.dto.work_schedule.WorkScheduleListResDto;
import com.thebizio.biziosalonms.enums.StatusEnum;
import com.thebizio.biziosalonms.projection.work_schedule.WorkScheduleDetailPrj;
import com.thebizio.biziosalonms.service.WorkScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/works-schedules")
public class WorkScheduleController {

    final WorkScheduleService workScheduleService;

    public WorkScheduleController(WorkScheduleService workScheduleService) {
        this.workScheduleService = workScheduleService;
    }

    @GetMapping
    List<WorkScheduleListResDto> listWorkSchedules(@RequestParam Map<String, String> filters) {
        return workScheduleService.list(filters);
    }

    @PostMapping
    WorkScheduleDetailDto createWorkSchedule(@RequestBody @Valid WorkScheduleCreateUpdateDto createDto) {
        return workScheduleService.saveWorkSchedule(createDto);
    }

    @GetMapping("/{wsId}")
    WorkScheduleDetailDto fetchWorkSchedule(@PathVariable UUID wsId) {
        return workScheduleService.fetchWorkSchedule(wsId);
    }

    @PutMapping("/{wsId}")
    void updateWorkSchedule(@PathVariable UUID wsId, @RequestBody @Valid WorkScheduleCreateUpdateDto dto) {
        workScheduleService.updateWorkSchedule(wsId, dto);
    }

    @PostMapping("/enable/{wsId}")
    public ResponseEntity<ResponseMessageDto> enableUser(@PathVariable(name = "wsId") UUID wsId){
        return ResponseEntity.ok(new ResponseMessageDto(workScheduleService.toggleWorkSchedule(wsId, StatusEnum.ENABLED)));
    }

    @PostMapping("/disable/{wsId}")
    public ResponseEntity<ResponseMessageDto> disableUser(@PathVariable(name = "wsId") UUID wsId) {
        return ResponseEntity.ok(new ResponseMessageDto(workScheduleService.toggleWorkSchedule(wsId, StatusEnum.DISABLED)));
    }
}

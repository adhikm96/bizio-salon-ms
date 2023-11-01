package com.thebizio.biziosalonms.service;

import com.thebizio.biziosalonms.entity.User;
import com.thebizio.biziosalonms.entity.WorkSchedule;
import com.thebizio.biziosalonms.exception.NotFoundException;
import com.thebizio.biziosalonms.repo.WorkScheduleRepo;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class WorkScheduleService {
    final WorkScheduleRepo workScheduleRepo;

    public WorkScheduleService(WorkScheduleRepo workScheduleRepo) {
        this.workScheduleRepo = workScheduleRepo;
    }

    public WorkSchedule findById(UUID id) {
        return workScheduleRepo.findById(id).orElseThrow(() -> new NotFoundException("work-schedule not found"));
    }
}

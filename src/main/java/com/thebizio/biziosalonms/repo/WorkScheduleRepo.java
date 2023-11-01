package com.thebizio.biziosalonms.repo;

import com.thebizio.biziosalonms.entity.WorkSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WorkScheduleRepo extends JpaRepository<WorkSchedule, UUID> {
}

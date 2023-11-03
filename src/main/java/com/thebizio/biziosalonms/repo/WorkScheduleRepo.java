package com.thebizio.biziosalonms.repo;

import com.thebizio.biziosalonms.entity.WorkSchedule;
import com.thebizio.biziosalonms.projection.work_schedule.WorkScheduleDetailPrj;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface WorkScheduleRepo extends JpaRepository<WorkSchedule, UUID>, JpaSpecificationExecutor<WorkSchedule> {
    @Query("select ws.id as id, ws.status as status, ws.name as name from WorkSchedule ws where ws.id = :wsId")
    Optional<WorkScheduleDetailPrj> fetchById(UUID wsId);
}

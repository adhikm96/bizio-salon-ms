package com.thebizio.biziosalonms.repo;

import com.thebizio.biziosalonms.entity.TaxSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface TaxScheduleRepo extends JpaRepository<TaxSchedule, UUID>, JpaSpecificationExecutor<TaxSchedule> {
}

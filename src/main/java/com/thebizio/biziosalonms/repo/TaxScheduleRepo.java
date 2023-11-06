package com.thebizio.biziosalonms.repo;

import com.thebizio.biziosalonms.entity.Branch;
import com.thebizio.biziosalonms.entity.TaxSchedule;
import com.thebizio.biziosalonms.enums.StatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface TaxScheduleRepo extends JpaRepository<TaxSchedule, UUID>, JpaSpecificationExecutor<TaxSchedule> {
    Optional<TaxSchedule> findByBranchAndStatus(Branch branch, StatusEnum status);

    boolean existsByBranchIdAndStatus(UUID branchId,StatusEnum status);

    boolean existsByBranchAndStatus(Branch branch,StatusEnum status);
}

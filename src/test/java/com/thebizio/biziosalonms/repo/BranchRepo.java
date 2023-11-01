package com.thebizio.biziosalonms.repo;

import com.thebizio.biziosalonms.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BranchRepo extends JpaRepository<Branch, UUID> {
}

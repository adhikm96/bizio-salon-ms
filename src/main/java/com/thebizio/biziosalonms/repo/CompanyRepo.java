package com.thebizio.biziosalonms.repo;

import com.thebizio.biziosalonms.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CompanyRepo extends JpaRepository<Company, UUID> {
}

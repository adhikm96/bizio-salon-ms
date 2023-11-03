package com.thebizio.biziosalonms.repo;

import com.thebizio.biziosalonms.entity.WorkScheduleItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WsItemRepo extends JpaRepository<WorkScheduleItem, UUID> {
}

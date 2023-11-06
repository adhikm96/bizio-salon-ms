package com.thebizio.biziosalonms.repo;

import com.thebizio.biziosalonms.entity.TaxSchedule;
import com.thebizio.biziosalonms.entity.TaxScheduleItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TaxScheduleItemRepo extends JpaRepository<TaxScheduleItem, UUID> {

    List<TaxScheduleItem> findAllByTaxSchedule(TaxSchedule taxSchedule);
}

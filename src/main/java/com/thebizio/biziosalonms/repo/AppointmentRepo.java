package com.thebizio.biziosalonms.repo;

import com.thebizio.biziosalonms.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface AppointmentRepo extends JpaRepository<Appointment, UUID>, JpaSpecificationExecutor<Appointment> {
    
}

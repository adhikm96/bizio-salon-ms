package com.thebizio.biziosalonms.repo;

import com.thebizio.biziosalonms.entity.Appointment;
import com.thebizio.biziosalonms.enums.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import com.thebizio.biziosalonms.entity.Invoice;
import java.util.Optional;
import java.util.UUID;

public interface AppointmentRepo extends JpaRepository<Appointment, UUID>, JpaSpecificationExecutor<Appointment> {

    Optional<Appointment> findByInvoice(Invoice invoice);

    List<Appointment> findAllByStatusAndAppointmentDateBeforeAndAppointmentTimeBefore(AppointmentStatus status, LocalDate appointmentDate, LocalTime appointmentTime);
}

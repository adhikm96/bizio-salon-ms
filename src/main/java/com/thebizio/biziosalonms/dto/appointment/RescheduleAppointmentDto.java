package com.thebizio.biziosalonms.dto.appointment;

import com.thebizio.biziosalonms.dto.customer.CustomerListDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RescheduleAppointmentDto {

    private UUID id;
    private CustomerListDto customer;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
}

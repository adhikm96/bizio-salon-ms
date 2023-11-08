package com.thebizio.biziosalonms.dto.appointment;

import com.thebizio.biziosalonms.dto.branch.BranchListDto;
import com.thebizio.biziosalonms.dto.customer.CustomerListDto;
import com.thebizio.biziosalonms.dto.invoice.InvoiceListDto;
import com.thebizio.biziosalonms.dto.salon_user.UserListDto;
import com.thebizio.biziosalonms.enums.AppointmentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Data@AllArgsConstructor@NoArgsConstructor
public class AppointmentListDto {

    private UUID id;
    private CustomerListDto customer;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private BranchListDto branch;
    private AppointmentStatus status;

    private UserListDto assignedTo; // Salon User

    private LocalTime expectedStartTime;
    private LocalTime expectedEndTime;
    private LocalTime startTime;
    private LocalTime endTime;

    private String cancellationFrom; // Customer | Salon

    private String rescheduledBy; //Customer | Salon

    private RescheduleAppointmentDto rescheduledWith;

    private InvoiceListDto invoice;

}

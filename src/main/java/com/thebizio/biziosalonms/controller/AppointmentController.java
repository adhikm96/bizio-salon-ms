package com.thebizio.biziosalonms.controller;

import com.thebizio.biziosalonms.dto.ResponseMessageDto;
import com.thebizio.biziosalonms.dto.appointment.AppointmentDetailDto;
import com.thebizio.biziosalonms.dto.appointment.AppointmentListDto;
import com.thebizio.biziosalonms.dto.appointment.CancelReasonDto;
import com.thebizio.biziosalonms.dto.appointment.CreateAppointmentDto;
import com.thebizio.biziosalonms.dto.customer.CreateUpdateCustomerDto;
import com.thebizio.biziosalonms.enums.AppointmentStatus;
import com.thebizio.biziosalonms.enums.StatusEnum;
import com.thebizio.biziosalonms.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping
    ResponseEntity<List<AppointmentListDto>> getAll(@RequestParam Optional<AppointmentStatus> status,
                                                    @RequestParam Optional<UUID> customer,
                                                    @RequestParam Optional<LocalDate>appointmentDate,
                                                    @RequestParam Optional<LocalTime> appointmentTime,
                                                    @RequestParam Optional<UUID> branch,
                                                    @RequestParam Optional<LocalTime> startTime,
                                                    @RequestParam Optional<LocalTime> endTime,
                                                    @RequestParam Optional<UUID> assignedTo,
                                                    @RequestParam Optional<UUID> invoice) {
        return ResponseEntity.ok(appointmentService.getAllAppointment(status,customer,appointmentDate,
                appointmentTime,branch,startTime,endTime,assignedTo,invoice));
    }

    @GetMapping("/{appointmentId}")
    ResponseEntity<AppointmentDetailDto> getById(@PathVariable UUID appointmentId) {
        return ResponseEntity.ok(appointmentService.getAppointmentById(appointmentId));
    }

    @PostMapping("/create")
    ResponseEntity<AppointmentDetailDto> create(@RequestBody @Valid CreateAppointmentDto dto) {
        return ResponseEntity.ok(appointmentService.createAppointment(dto));
    }

    @PostMapping("/{appointmentId}/assign/{salonUserId}")
    ResponseEntity<ResponseMessageDto> assign(@PathVariable UUID appointmentId,@PathVariable UUID salonUserId) {
        return ResponseEntity.ok(new ResponseMessageDto(appointmentService.assignAppointment(appointmentId,salonUserId)));
    }

    @PostMapping("/{appointmentId}/un-assign")
    ResponseEntity<ResponseMessageDto> unAssign(@PathVariable UUID appointmentId) {
        return ResponseEntity.ok(new ResponseMessageDto(appointmentService.unAssignAppointment(appointmentId)));
    }

    @PostMapping("/{appointmentId}/start")
    ResponseEntity<ResponseMessageDto> start(@PathVariable UUID appointmentId) {
        return ResponseEntity.ok(new ResponseMessageDto(appointmentService.startAppointment(appointmentId)));
    }

    @PostMapping("/{appointmentId}/cancel")
    ResponseEntity<ResponseMessageDto> cancel(@PathVariable UUID appointmentId,@RequestBody @Valid CancelReasonDto dto) {
        return ResponseEntity.ok(new ResponseMessageDto(appointmentService.cancelAppointment(appointmentId,dto)));
    }

    @PostMapping("/{appointmentId}/reschedule/{rescheduleAppointmentId}")
    ResponseEntity<ResponseMessageDto> reschedule(@PathVariable UUID appointmentId,@PathVariable UUID rescheduleAppointmentId) {
        return ResponseEntity.ok(new ResponseMessageDto(appointmentService.rescheduleAppointment(appointmentId,rescheduleAppointmentId)));
    }


}

package com.thebizio.biziosalonms.dto.appointment;

import com.thebizio.biziosalonms.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data@AllArgsConstructor@NoArgsConstructor
public class CreateAppointmentDto {

    @NotNull
    private UUID customerId;
    @NotNull
    private LocalDate date;
    @NotNull
    private LocalTime time;
    @NotNull
    private UUID branchId;
    private List<UUID> productAndServices = new ArrayList<>();
    private String notes;
}

package com.thebizio.biziosalonms.dto.appointment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data@AllArgsConstructor@NoArgsConstructor
public class AppointmentIdDto {

    @NotNull
    private UUID appointmentId;
}

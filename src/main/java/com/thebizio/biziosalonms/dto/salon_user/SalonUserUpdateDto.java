package com.thebizio.biziosalonms.dto.salon_user;

import com.thebizio.biziosalonms.enums.EmpType;
import com.thebizio.biziosalonms.enums.PaySchedule;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@NoArgsConstructor
public class SalonUserUpdateDto {

    @NotNull @NotBlank
    private String empCode;

    @NotNull
    private EmpType empType;

    @NotNull
    private PaySchedule paySchedule;

    @NotNull @NotBlank
    private String designation;

    private UUID branchId;
    private UUID workScheduleId;
}

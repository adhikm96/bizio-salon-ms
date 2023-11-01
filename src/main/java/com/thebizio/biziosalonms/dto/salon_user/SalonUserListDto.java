package com.thebizio.biziosalonms.dto.salon_user;

import com.thebizio.biziosalonms.enums.EmpType;
import com.thebizio.biziosalonms.enums.PaySchedule;
import com.thebizio.biziosalonms.enums.StatusEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SalonUserListDto {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String mobile;

    private String bizioId;
    private String empCode;
    private EmpType empType;
    private PaySchedule paySchedule;
    private String designation;
    private StatusEnum status;
}

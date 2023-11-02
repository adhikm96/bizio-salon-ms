package com.thebizio.biziosalonms.dto.branch;

import com.thebizio.biziosalonms.dto.address.AddressDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@NoArgsConstructor
public class BranchCreateUpdateDto {

    @NotNull @NotBlank
    private String name;
    private String contactNo;

    @NotNull @NotBlank @Email
    private String email;

    private AddressDto address;

    @NotNull
    private UUID companyId;

    @NotNull
    private UUID workScheduleId;
}

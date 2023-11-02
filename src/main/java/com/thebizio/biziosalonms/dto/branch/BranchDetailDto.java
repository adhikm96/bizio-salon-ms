package com.thebizio.biziosalonms.dto.branch;

import com.thebizio.biziosalonms.dto.address.AddressDto;
import com.thebizio.biziosalonms.enums.BranchStatusEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BranchDetailDto {
    private String name;
    private String contactNo;
    private String email;
    private BranchStatusEnum status;
    private AddressDto address;
}

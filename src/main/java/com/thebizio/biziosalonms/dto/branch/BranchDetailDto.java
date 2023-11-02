package com.thebizio.biziosalonms.dto.branch;

import com.thebizio.biziosalonms.dto.address.AddressDto;
import com.thebizio.biziosalonms.enums.BranchStatusEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BranchDetailDto {
    private String id;
    private String name;
    private String contactNo;
    private String email;
    private BranchStatusEnum status;
    private String streetAddress1;
    private String streetAddress2;
    private String city;
    private String state;
    private String country;
    private String zipcode;
}

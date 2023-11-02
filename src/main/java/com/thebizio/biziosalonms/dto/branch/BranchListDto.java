package com.thebizio.biziosalonms.dto.branch;

import com.thebizio.biziosalonms.enums.BranchStatusEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class BranchListDto {
    private UUID id;
    private String name;
    private String contactNo;
    private String email;
    private BranchStatusEnum status;
    private String zipcode;
}

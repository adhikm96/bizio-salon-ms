package com.thebizio.biziosalonms.dto.branch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data@AllArgsConstructor@NoArgsConstructor
public class BranchListDto {

    private UUID id;
    private String name;
}

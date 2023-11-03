package com.thebizio.biziosalonms.dto.tax_schedule;

import com.thebizio.biziosalonms.dto.branch.BranchListDto;
import com.thebizio.biziosalonms.entity.Branch;
import com.thebizio.biziosalonms.enums.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.UUID;

@Data@NoArgsConstructor
public class TaxScheduleLitDto {

    private UUID id;
    private String name;
    private StatusEnum status;
    private BranchListDto branch;

    public TaxScheduleLitDto(UUID id, String name, StatusEnum status, Branch branch) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.branch = branch == null ? null:new ModelMapper().map(branch,BranchListDto.class);
    }
}

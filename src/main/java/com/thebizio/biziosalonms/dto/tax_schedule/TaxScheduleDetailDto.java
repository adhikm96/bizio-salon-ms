package com.thebizio.biziosalonms.dto.tax_schedule;

import com.thebizio.biziosalonms.dto.appointment.AppointmentListDto;
import com.thebizio.biziosalonms.dto.branch.BranchListDto;
import com.thebizio.biziosalonms.dto.tax_schedule_item.TaxScheduleItemDetailDto;
import com.thebizio.biziosalonms.entity.Branch;
import com.thebizio.biziosalonms.entity.TaxScheduleItem;
import com.thebizio.biziosalonms.enums.StatusEnum;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class TaxScheduleDetailDto {

    private UUID id;
    private String name;
    private StatusEnum status;
    private BranchListDto branch;
    private List<TaxScheduleItemDetailDto> taxScheduleItems;

    public TaxScheduleDetailDto(UUID id, String name, StatusEnum status, Branch branch, List<TaxScheduleItem> taxScheduleItems) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.branch = branch == null ? null:new ModelMapper().map(branch,BranchListDto.class);
        this.taxScheduleItems = taxScheduleItems == null || taxScheduleItems.isEmpty() ? new ArrayList<>():new ModelMapper().map(taxScheduleItems,new TypeToken<List<TaxScheduleItemDetailDto>>(){}.getType());
    }
}

package com.thebizio.biziosalonms.dto.checkout;

import com.thebizio.biziosalonms.dto.tax_schedule_item.ItemTaxDetailDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data@AllArgsConstructor@NoArgsConstructor
public class TaxStringDto {

    private List<ItemTaxDetailDto> itemTaxes = new ArrayList<>();
    private List<GrossTotalTaxDetailDto> grossTotalTaxes = new ArrayList<>();
}

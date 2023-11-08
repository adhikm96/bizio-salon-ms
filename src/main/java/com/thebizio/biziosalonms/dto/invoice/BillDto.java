package com.thebizio.biziosalonms.dto.invoice;

import com.thebizio.biziosalonms.dto.checkout.TaxStringDto;
import com.thebizio.biziosalonms.entity.Branch;
import com.thebizio.biziosalonms.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data@AllArgsConstructor@NoArgsConstructor
public class BillDto {

    private Branch branch;
    private Double grossTotal;
    private Double discount;
    private String discountStr;
    private Double taxes;
    private Double netTotal;
    private TaxStringDto taxStringDto;
    private List<Item> purchasedItem;
}

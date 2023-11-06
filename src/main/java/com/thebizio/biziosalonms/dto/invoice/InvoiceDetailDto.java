package com.thebizio.biziosalonms.dto.invoice;

import com.thebizio.biziosalonms.dto.branch.BranchListDto;
import com.thebizio.biziosalonms.dto.customer.CustomerListDto;
import com.thebizio.biziosalonms.entity.Item;
import com.thebizio.biziosalonms.enums.InvoiceStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data@AllArgsConstructor@NoArgsConstructor
public class InvoiceDetailDto {

    private UUID id;
    private BranchListDto branch;
    private LocalDate postingDate;
    private CustomerListDto customerUser;
    private String customerName;
    private List<Item> items;
    private Double grossTotal;
    private Double discount;
    private String discountStr;
    private Double taxes;
    private String taxStr;
    private Double netTotal;
    private InvoiceStatus status;;
}

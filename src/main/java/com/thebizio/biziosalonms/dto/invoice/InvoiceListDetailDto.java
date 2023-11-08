package com.thebizio.biziosalonms.dto.invoice;

import com.thebizio.biziosalonms.dto.appointment.AppointmentListDto;
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
public class InvoiceListDetailDto {

    private UUID id;
    private BranchListDto branch;
    private LocalDate postingDate;
    private List<AppointmentListDto> appointments;
    private CustomerListDto customerUser;
    private String customerName;
    private Double grossTotal;
    private Double discount;
    private Double taxes;
    private Double netTotal;
    private InvoiceStatus status;;
}

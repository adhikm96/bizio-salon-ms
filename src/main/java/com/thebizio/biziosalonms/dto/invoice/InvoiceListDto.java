package com.thebizio.biziosalonms.dto.invoice;

import com.thebizio.biziosalonms.enums.InvoiceStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceListDto {

    private UUID id;
    private LocalDate postingDate;
    private Double netTotal;
    private InvoiceStatus status;
}

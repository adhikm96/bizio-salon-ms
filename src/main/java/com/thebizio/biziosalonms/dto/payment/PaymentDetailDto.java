package com.thebizio.biziosalonms.dto.payment;

import com.thebizio.biziosalonms.dto.invoice.InvoiceDetailDto;
import com.thebizio.biziosalonms.enums.PaymentTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data@AllArgsConstructor@NoArgsConstructor
public class PaymentDetailDto {

    private UUID id;
    private PaymentTypeEnum paymentType;
    private LocalDate paymentDate;
    private String paymentRef;
    private InvoiceDetailDto invoice;
}

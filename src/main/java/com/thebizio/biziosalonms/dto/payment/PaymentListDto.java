package com.thebizio.biziosalonms.dto.payment;

import com.thebizio.biziosalonms.enums.PaymentTypeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
public class PaymentListDto {
    private PaymentTypeEnum paymentType;
    private LocalDate paymentDate;
    private String paymentRef;
    private UUID invoiceId;
}

package com.thebizio.biziosalonms.dto.checkout;

import com.thebizio.biziosalonms.enums.PaymentTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data@AllArgsConstructor@NoArgsConstructor
public class CheckoutPaymentDto {

    private UUID invoiceId;
    private PaymentTypeEnum paymentType;
}

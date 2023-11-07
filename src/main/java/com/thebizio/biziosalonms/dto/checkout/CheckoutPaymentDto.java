package com.thebizio.biziosalonms.dto.checkout;

import com.thebizio.biziosalonms.enums.PaymentTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data@AllArgsConstructor@NoArgsConstructor
public class CheckoutPaymentDto {

    @NotNull
    private UUID invoiceId;

    @NotNull
    private PaymentTypeEnum paymentType;
}

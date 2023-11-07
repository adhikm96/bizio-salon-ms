package com.thebizio.biziosalonms.dto.checkout;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Data@AllArgsConstructor@NoArgsConstructor
public class CheckoutSessionDto {

    @NotNull@NotEmpty
    private List<UUID> items;
    private String promoCode;

    @NotNull
    private UUID appointmentId;
}

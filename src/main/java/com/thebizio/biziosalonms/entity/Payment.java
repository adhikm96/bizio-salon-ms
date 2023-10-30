package com.thebizio.biziosalonms.entity;

import com.thebizio.biziosalonms.enums.PaymentTypeEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Table(name = "payments")
@Getter
@Setter
public class Payment extends LastUpdateDetail{

    @Id
    @GeneratedValue(generator = "uuid4")
    @Column(columnDefinition = "uuid")
    private UUID id;

    private PaymentTypeEnum paymentType;
    private LocalDate paymentDate;
    private String paymentRef;

    @ManyToOne
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;
}

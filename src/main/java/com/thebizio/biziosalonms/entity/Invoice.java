package com.thebizio.biziosalonms.entity;

import com.thebizio.biziosalonms.enums.InvoiceStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "invoices")
@Getter
@Setter
@NoArgsConstructor
public class Invoice extends LastUpdateDetail {

    @Id
    @GeneratedValue(generator = "uuid4")
    @Column(columnDefinition = "uuid")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;

    private LocalDate postingDate;

    @ManyToOne
    @JoinColumn
    private Appointment appointment;

    @ManyToOne
    @JoinColumn
    private CustomerUser customerUser;

    private String customerName;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "invoice_items",
            joinColumns = { @JoinColumn(name = "invoice_id") },
            inverseJoinColumns = { @JoinColumn(name = "item_id") }
    )
    private List<Item> items;

    private Double grossTotal;

    private Double discount;

    private String discountStr;

    private Double taxes;

    private String taxStr;

    private Double netTotal;

    private InvoiceStatus status;
}

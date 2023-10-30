package com.thebizio.biziosalonms.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.thebizio.biziosalonms.enums.AppointmentStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "appointments")
@Getter
@Setter
@NoArgsConstructor
public class Appointment extends LastUpdateDetail{

    @Id
    @GeneratedValue(generator = "uuid4")
    @Column(columnDefinition = "uuid")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private CustomerUser customerUser;

    private LocalDate appointmentDate;

    private LocalTime appointmentTime;

    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "appointment_product_services",
            joinColumns = { @JoinColumn(name = "appointment_id") },
            inverseJoinColumns = { @JoinColumn(name = "item_id") }
    )
    private List<Item> productAndServices;  // Products & Services

    private AppointmentStatus status;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "appointment_purchases",
            joinColumns = { @JoinColumn(name = "appointment_id") },
            inverseJoinColumns = { @JoinColumn(name = "item_id") }
    )
    private List<Item> purchases;

    @Column(columnDefinition = "text")
    private String notes;

    @ManyToOne
    @JoinColumn(name = "assigned_to")
    private User assignedTo; // Salon User

    private LocalTime startTime;
    private LocalTime endTime;

    private String cancellationFrom; // Customer | Salon

    private String cancellationReason;

    private String customerFeedback;

    private String rescheduledBy; //Customer | Salon

    @ManyToOne
    @JoinColumn(name = "rescheduled_with")
    @JsonBackReference
    private Appointment rescheduledWith;

    @ManyToOne
    @JoinColumn(name = "invoice_id")
    @JsonBackReference
    private Invoice invoice;
}

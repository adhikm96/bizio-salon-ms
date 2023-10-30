package com.thebizio.biziosalonms.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "appointment_items")
@Getter
@Setter
@NoArgsConstructor
public class AppointmentItem {
    @Id
    @GeneratedValue(generator = "uuid4")
    @Column(columnDefinition = "uuid")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "appointment_id")
    Appointment appointment;

    @ManyToOne
    @JoinColumn(name = "item_id")
    Item item;
}

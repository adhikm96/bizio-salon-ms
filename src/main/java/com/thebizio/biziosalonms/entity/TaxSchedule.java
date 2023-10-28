package com.thebizio.biziosalonms.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Table(name = "tax_schedules")
@Getter
@Setter
public class TaxSchedule extends LastUpdateDetail{

    @Id
    @GeneratedValue(generator = "uuid4")
    @Column(columnDefinition = "uuid")
    private UUID id;

    private String name;


    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;
}

package com.thebizio.biziosalonms.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.thebizio.biziosalonms.enums.BranchStatusEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Table(name = "branches")
@Getter
@Setter
public class Branch extends LastUpdateDetail{

    @Id
    @GeneratedValue(generator = "uuid4")
    @Column(columnDefinition = "uuid")
    private UUID id;

    private String name;

    @Column(name = "contact_no")
    private String contactNo;
    private String email;
    private BranchStatusEnum status;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "work_schedule_id")
    private WorkSchedule workSchedule;

    @ManyToOne
    @JoinColumn
    @JsonBackReference
    private Address address;
}

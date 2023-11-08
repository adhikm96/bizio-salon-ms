package com.thebizio.biziosalonms.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.thebizio.biziosalonms.enums.EmpType;
import com.thebizio.biziosalonms.enums.GenderEnum;
import com.thebizio.biziosalonms.enums.PaySchedule;
import com.thebizio.biziosalonms.enums.StatusEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User extends LastUpdateDetail{

    @Id
    @GeneratedValue(generator = "uuid4")
    @Column(columnDefinition = "uuid")
    private UUID id;

    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String mobile;
    private GenderEnum gender;

    private String avatar;
    private String bizioId;
    private String empCode;
    private EmpType empType;
    private PaySchedule paySchedule;
    private String designation;
    private StatusEnum status;

    @ManyToOne
    @JoinColumn(name = "work_schedule_id")
    @JsonBackReference
    private WorkSchedule workSchedule;

    @ManyToOne
    @JoinColumn(name = "branch_id")
    @JsonBackReference
    private Branch branch;

    @ManyToOne
    @JoinColumn(name = "address_id")
    @JsonBackReference
    private Address address;
}

package com.thebizio.biziosalonms.entity;

import com.thebizio.biziosalonms.enums.StatusEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Table(name = "work_schedules")
@Getter
@Setter
public class WorkSchedule extends LastUpdateDetail{

    @Id
    @GeneratedValue(generator = "uuid4")
    @Column(columnDefinition = "uuid")
    private UUID id;

    private String name;
    private StatusEnum status;

    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;
}

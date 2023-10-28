package com.thebizio.biziosalonms.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.thebizio.biziosalonms.enums.WorkScheduleDayEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Table(name = "work_schedule_items")
@Getter
@Setter
public class WorkScheduleItem extends LastUpdateDetail{

    @Id
    @GeneratedValue(generator = "uuid4")
    @Column(columnDefinition = "uuid")
    private UUID id;

    private WorkScheduleDayEnum day;
    private LocalTime startTime;
    private LocalTime breakStartTime;
    private LocalTime breakEndTime;
    private LocalTime endTime;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "work_schedule_id")
    private WorkSchedule workSchedule;
}

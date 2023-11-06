package com.thebizio.biziosalonms.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.thebizio.biziosalonms.enums.StatusEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
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

    @JsonIgnore
    @JsonManagedReference
    @OneToMany(mappedBy = "workSchedule", fetch = FetchType.LAZY)
    private List<Branch> branches;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "workSchedule")
    private List<WorkScheduleItem> workScheduleItems = new ArrayList<>();

    public void appendItem(WorkScheduleItem item) {
        item.setWorkSchedule(this);
        this.getWorkScheduleItems().add(item);
    }

    public void clearItems() {
        this.getWorkScheduleItems().forEach(i -> i.setWorkSchedule(null));
        this.getWorkScheduleItems().clear();
    }
}

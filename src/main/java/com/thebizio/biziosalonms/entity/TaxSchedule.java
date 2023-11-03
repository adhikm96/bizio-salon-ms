package com.thebizio.biziosalonms.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.thebizio.biziosalonms.enums.StatusEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
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

    private StatusEnum status;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "taxSchedule")
    private List<TaxScheduleItem> taxScheduleItems;
}

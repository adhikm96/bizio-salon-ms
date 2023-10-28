package com.thebizio.biziosalonms.entity;

import com.thebizio.biziosalonms.enums.TaxChargeTypeEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Table(name = "tax_schedule_items")
@Getter
@Setter
public class TaxScheduleItem extends LastUpdateDetail{

    @Id
    @GeneratedValue(generator = "uuid4")
    @Column(columnDefinition = "uuid")
    private UUID id;

    private TaxChargeTypeEnum taxChargeType;
    private float value;

    private float onValueFrom;
    private float onValueTo;

    @ManyToOne
    @JoinColumn(name = "tax_head_id")
    private TaxHead taxHead;

    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;
}

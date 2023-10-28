package com.thebizio.biziosalonms.entity;

import com.thebizio.biziosalonms.enums.ChargeOnEnum;
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
@Table(name = "tax_heads")
@Getter
@Setter
public class TaxHead extends LastUpdateDetail{

    @Id
    @GeneratedValue(generator = "uuid4")
    @Column(columnDefinition = "uuid")
    private UUID id;

    private String name;

    @Column(nullable = false, unique = true)
    private String code;

    private ChargeOnEnum chargeOn;
    private StatusEnum status;
}

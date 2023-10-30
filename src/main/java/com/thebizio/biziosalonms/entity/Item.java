package com.thebizio.biziosalonms.entity;

import com.thebizio.biziosalonms.enums.ItemType;
import com.thebizio.biziosalonms.enums.StatusEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "items")
@Getter
@Setter
@NoArgsConstructor
public class Item extends LastUpdateDetail {

    @Id
    @GeneratedValue(generator = "uuid4")
    @Column(columnDefinition = "uuid")
    private UUID id;

    private String itemName;
    private String itemCode;
    private ItemType itemType;
    private Double price;
    private Double cost;
    private String description;
    private String brand;
    private StatusEnum status;
}

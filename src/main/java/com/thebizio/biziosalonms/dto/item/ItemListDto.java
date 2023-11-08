package com.thebizio.biziosalonms.dto.item;

import com.thebizio.biziosalonms.enums.ItemType;
import com.thebizio.biziosalonms.enums.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data@AllArgsConstructor@NoArgsConstructor
public class ItemListDto {

    private UUID id;
    private String name;
    private String code;
    private ItemType itemType;
    private Double price;
    private Double cost;
    private String description;
}

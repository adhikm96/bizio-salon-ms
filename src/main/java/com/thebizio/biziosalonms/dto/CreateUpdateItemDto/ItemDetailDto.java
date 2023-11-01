package com.thebizio.biziosalonms.dto.CreateUpdateItemDto;

import com.thebizio.biziosalonms.enums.ItemType;
import com.thebizio.biziosalonms.enums.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDetailDto {

    private UUID id;
    private String name;
    private String code;
    private ItemType itemType;
    private Double price;
    private Double cost;
    private String description;
    private String brand;
    private StatusEnum status;
}

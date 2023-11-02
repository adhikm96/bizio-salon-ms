package com.thebizio.biziosalonms.dto.item;

import com.thebizio.biziosalonms.enums.ItemType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Data@AllArgsConstructor@NoArgsConstructor
public class CreateUpdateItemDto {

    @NotNull@NotBlank
    private String name;
    @NotNull@NotBlank
    private String code;
    @NotNull
    private ItemType itemType;
    @NotNull@PositiveOrZero
    private Double price;
    @NotNull@PositiveOrZero
    private Double cost;
    private String description;
    private String brand;
}

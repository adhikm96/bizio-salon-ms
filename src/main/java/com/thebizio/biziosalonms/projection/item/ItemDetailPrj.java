package com.thebizio.biziosalonms.projection.item;

import com.thebizio.biziosalonms.enums.ItemType;
import com.thebizio.biziosalonms.enums.StatusEnum;

import java.util.UUID;

public interface ItemDetailPrj {

    UUID getId();
    String getName();
    String getCode();
    ItemType getItemType();
    Double getPrice();
    Double getCost();
    String getDescription();
    String getBrand();
    StatusEnum getStatus();
}

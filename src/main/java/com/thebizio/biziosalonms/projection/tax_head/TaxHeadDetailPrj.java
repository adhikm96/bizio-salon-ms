package com.thebizio.biziosalonms.projection.tax_head;

import com.thebizio.biziosalonms.enums.ChargeOnEnum;
import com.thebizio.biziosalonms.enums.StatusEnum;

import javax.persistence.Column;
import java.util.UUID;

public interface TaxHeadDetailPrj {

    UUID getId();
    String getName();
    String getCode();
    ChargeOnEnum getChargeOn();
    StatusEnum getStatus();
}

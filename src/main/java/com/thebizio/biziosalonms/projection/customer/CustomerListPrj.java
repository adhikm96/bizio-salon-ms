package com.thebizio.biziosalonms.projection.customer;

import com.thebizio.biziosalonms.enums.FederationEnum;
import com.thebizio.biziosalonms.enums.StatusEnum;

import java.util.UUID;

public interface CustomerListPrj {

    UUID getId();
    String getFirstName();
    String getLastName();
    String getUsername();
    String getEmail();
    String getMobile();
    String getZipcode();
    FederationEnum getFederation();
    StatusEnum getStatus();
}

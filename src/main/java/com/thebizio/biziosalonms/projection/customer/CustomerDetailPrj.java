package com.thebizio.biziosalonms.projection.customer;

import com.thebizio.biziosalonms.enums.FederationEnum;
import com.thebizio.biziosalonms.enums.GenderEnum;
import com.thebizio.biziosalonms.enums.StatusEnum;

import java.util.UUID;

public interface CustomerDetailPrj {

    UUID getId();
    String getFirstName();
    String getLastName();
    String getUsername();
    String getEmail();
    String getMobile();
    GenderEnum getGender();
    String getStreetAddress1();
    String getStreetAddress2();
    String getCity();
    String getState();
    String getCountry();
    String getZipcode();
    FederationEnum getFederation();
    StatusEnum getStatus();
}

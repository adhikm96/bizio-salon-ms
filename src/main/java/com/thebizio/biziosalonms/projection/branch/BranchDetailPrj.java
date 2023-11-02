package com.thebizio.biziosalonms.projection.branch;

import com.thebizio.biziosalonms.enums.BranchStatusEnum;

import java.util.UUID;

public interface BranchDetailPrj {
    UUID getId();
    String getName();
    String getContactNo();
    String getEmail();
    BranchStatusEnum getStatus();
    String getStreetAddress1();
    String getStreetAddress2();
    String getCity();
    String getState();
    String getCountry();
    String getZipcode();
}

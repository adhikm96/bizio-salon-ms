package com.thebizio.biziosalonms.projection.company;

import java.util.UUID;

public interface CompanyDetailPrj {
    UUID getId();
    String getName();
    String getStreetAddress1();
    String getStreetAddress2();
    String getCity();
    String getState();
    String getCountry();
    String getZipcode();
    String getContactNo();
    String getEmail();
}

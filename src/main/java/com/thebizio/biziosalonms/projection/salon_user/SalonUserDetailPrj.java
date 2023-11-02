package com.thebizio.biziosalonms.projection.salon_user;

import com.thebizio.biziosalonms.enums.EmpType;
import com.thebizio.biziosalonms.enums.PaySchedule;
import com.thebizio.biziosalonms.enums.StatusEnum;

import java.util.UUID;

public interface SalonUserDetailPrj {
   UUID getId();
   String getFirstName();
   String getLastName();
   String getUsername();
   String getEmail();
   String getMobile();

   String getBizioId();
   String getEmpCode();
   EmpType getEmpType();
   PaySchedule getPaySchedule();
   String getDesignation();
   StatusEnum getStatus();

   String getStreetAddress1();
   String getStreetAddress2();
   String getCity();
   String getState();
   String getCountry();
   String getZipcode();
}

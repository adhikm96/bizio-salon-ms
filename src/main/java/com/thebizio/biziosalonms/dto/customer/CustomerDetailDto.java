package com.thebizio.biziosalonms.dto.customer;

import com.thebizio.biziosalonms.enums.FederationEnum;
import com.thebizio.biziosalonms.enums.GenderEnum;
import com.thebizio.biziosalonms.enums.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data@AllArgsConstructor@NoArgsConstructor
public class CustomerDetailDto {

    private UUID id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String mobile;
    private GenderEnum gender;
    private String streetAddress1;
    private String streetAddress2;
    private String city;
    private String state;
    private String country;
    private String zipcode;
    private FederationEnum federation;
    private StatusEnum status;
}

package com.thebizio.biziosalonms.dto.Customer;

import com.thebizio.biziosalonms.enums.FederationEnum;
import com.thebizio.biziosalonms.enums.GenderEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data@AllArgsConstructor@NoArgsConstructor
public class CreateUpdateCustomerDto {

    @NotNull @NotBlank
    private String firstName;
    @NotNull @NotBlank
    private String lastName;

    private String username;

    @NotNull @NotBlank @Email
    private String email;
    @NotNull @NotBlank
    private String mobile;

    @NotNull
    private GenderEnum gender;

    private String streetAddress1;
    private String streetAddress2;
    private String city;
    private String state;
    private String country;
    private String zipcode;

    @NotNull
    private FederationEnum federation;
}

package com.thebizio.biziosalonms.dto.company;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class CompanyUpdateDto {

    @NotNull @NotBlank
    private String name;
    private String streetAddress1;
    private String streetAddress2;
    private String city;
    private String state;
    private String country;
    private String zipcode;
    private String contactNo;

    @NotNull @NotBlank
    private String email;
}

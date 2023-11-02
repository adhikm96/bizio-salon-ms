package com.thebizio.biziosalonms.dto.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data@AllArgsConstructor@NoArgsConstructor
public class CustomerListDto {

    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
}

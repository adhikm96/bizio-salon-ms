package com.thebizio.biziosalonms.dto.salon_user;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserListDto {

    private String firstName;
    private String lastName;
    private String email;
    private String empCode;
    private String designation;
}

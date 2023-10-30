package com.thebizio.biziosalonms.entity;

import com.thebizio.biziosalonms.enums.FederationEnum;
import com.thebizio.biziosalonms.enums.GenderEnum;
import com.thebizio.biziosalonms.enums.StatusEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
public class CustomerUser extends LastUpdateDetail {

    @Id
    @GeneratedValue(generator = "uuid4")
    @Column(columnDefinition = "uuid")
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

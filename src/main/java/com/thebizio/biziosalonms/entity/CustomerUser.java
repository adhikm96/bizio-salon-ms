package com.thebizio.biziosalonms.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    private FederationEnum federation;
    private StatusEnum status;

    @ManyToOne
    @JoinColumn(name = "address_id")
    @JsonBackReference
    private Address address;
}

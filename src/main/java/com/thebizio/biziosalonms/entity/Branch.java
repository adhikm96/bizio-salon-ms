package com.thebizio.biziosalonms.entity;

import com.thebizio.biziosalonms.enums.BranchStatusEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Table(name = "branches")
@Getter
@Setter
public class Branch extends LastUpdateDetail{

    @Id
    @GeneratedValue(generator = "uuid4")
    @Column(columnDefinition = "uuid")
    private UUID id;

    private String name;
    private String streetAddress1;
    private String streetAddress2;
    private String city;
    private String state;
    private String country;
    private String zipcode;
    private String contactNo;
    private String email;
    private BranchStatusEnum status;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
}

package com.thebizio.biziosalonms.repo;

import com.thebizio.biziosalonms.entity.Company;
import com.thebizio.biziosalonms.enums.StatusEnum;
import com.thebizio.biziosalonms.projection.company.CompanyDetailPrj;
import com.thebizio.biziosalonms.projection.company.CompanyListPrj;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CompanyRepo extends JpaRepository<Company, UUID>, JpaSpecificationExecutor<Company> {
    @Query("select company.id as id, company.name as name, address.streetAddress1 as streetAddress1, address.streetAddress2 as streetAddress2," +
            " address.city as city, address.state as state, address.country as country, address.zipcode as zipcode, company.contactNo as contactNo," +
            " company.email as email " +
            " from Company company left join company.address address order by company.modified")
    List<CompanyListPrj> fetchAll();

    @Query("select company.id as id, company.name as name, address.streetAddress1 as streetAddress1, address.streetAddress2 as streetAddress2," +
            " address.city as city, address.state as state, address.country as country, address.zipcode as zipcode, company.contactNo as contactNo," +
            " company.email as email " +
            " from Company company left join company.address address where company.status = :status order by company.modified")
    List<CompanyListPrj> fetchAllByStatus(StatusEnum status);

    @Query("select company.id as id, company.name as name, address.streetAddress1 as streetAddress1, address.streetAddress2 as streetAddress2," +
            " address.city as city, address.state as state, address.country as country, address.zipcode as zipcode, company.contactNo as contactNo," +
            " company.email as email " +
            " from Company company left join company.address address where company.name = :name order by company.modified")
    List<CompanyListPrj> fetchAllByName(String name);

    @Query("select company.id as id, company.name as name, address.streetAddress1 as streetAddress1, address.streetAddress2 as streetAddress2," +
            " address.city as city, address.state as state, address.country as country, address.zipcode as zipcode, company.contactNo as contactNo," +
            " company.email as email " +
            " from Company company left join company.address address where address.zipcode = :zipcode order by company.modified")
    List<CompanyListPrj> fetchAllByZipCode(String zipcode);

    @Query("select company.id as id, company.name as name, address.streetAddress1 as streetAddress1, address.streetAddress2 as streetAddress2," +
            " address.city as city, address.state as state, address.country as country, address.zipcode as zipcode, company.contactNo as contactNo," +
            " company.email as email " +
            " from Company company left join company.address address where company.name = :name and address.zipcode = :zipcode order by company.modified")
    List<CompanyListPrj> fetchAllByZipCodeName(String zipcode, String name);

    @Query("select company.id as id, company.name as name, address.streetAddress1 as streetAddress1, address.streetAddress2 as streetAddress2," +
            " address.city as city, address.state as state, address.country as country, address.zipcode as zipcode, company.contactNo as contactNo," +
            " company.email as email " +
            " from Company company left join company.address address where company.name = :name and company.status = :status order by company.modified")
    List<CompanyListPrj> fetchAllByStatusName(StatusEnum status, String name);


    @Query("select company.id as id, company.name as name, address.streetAddress1 as streetAddress1, address.streetAddress2 as streetAddress2," +
            " address.city as city, address.state as state, address.country as country, address.zipcode as zipcode, company.contactNo as contactNo," +
            " company.email as email " +
            " from Company company left join company.address address where company.status = :status and address.zipcode = :zipcode order by company.modified")
    List<CompanyListPrj> fetchAllByZipCodeStatus(String zipcode, StatusEnum status);

    @Query("select company.id as id, company.name as name, address.streetAddress1 as streetAddress1, address.streetAddress2 as streetAddress2," +
            " address.city as city, address.state as state, address.country as country, address.zipcode as zipcode, company.contactNo as contactNo," +
            " company.email as email " +
            " from Company company left join company.address address where company.status = :status and address.zipcode = :zipcode and company.name = :name order by company.modified")
    List<CompanyListPrj> fetchAllByZipCodeStatusName(String zipcode, StatusEnum status, String name);

    boolean existsByEmail(String email);

    @Query("select company.id as id, company.name as name, address.streetAddress1 as streetAddress1, address.streetAddress2 as streetAddress2," +
            " address.city as city, address.state as state, address.country as country, address.zipcode as zipcode, company.contactNo as contactNo," +
            " company.email as email " +
            " from Company company left join company.address address where company.id = :id")
    Optional<CompanyDetailPrj> fetchById(UUID id);
}



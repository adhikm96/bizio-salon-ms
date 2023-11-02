package com.thebizio.biziosalonms.repo;

import com.thebizio.biziosalonms.entity.Company;
import com.thebizio.biziosalonms.projection.company.CompanyDetailPrj;
import com.thebizio.biziosalonms.projection.company.CompanyListPrj;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CompanyRepo extends JpaRepository<Company, UUID> {
    @Query("select company.id as id, company.name as name, company.streetAddress1 as streetAddress1, company.streetAddress2 as streetAddress2," +
            " company.city as city, company.state as state, company.country as country, company.zipcode as zipcode, company.contactNo as contactNo," +
            " company.email as email " +
            " from Company company order by company.modified")
    List<CompanyListPrj> fetchAll();

    boolean existsByEmail(String email);

    @Query("select company.id as id, company.name as name, company.streetAddress1 as streetAddress1, company.streetAddress2 as streetAddress2," +
            " company.city as city, company.state as state, company.country as country, company.zipcode as zipcode, company.contactNo as contactNo," +
            " company.email as email " +
            " from Company company where company.id = :id")
    Optional<CompanyDetailPrj> fetchById(UUID id);
}



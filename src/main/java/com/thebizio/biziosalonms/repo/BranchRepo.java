package com.thebizio.biziosalonms.repo;

import com.thebizio.biziosalonms.entity.Branch;
import com.thebizio.biziosalonms.projection.branch.BranchDetailPrj;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BranchRepo extends JpaRepository<Branch, UUID>, JpaSpecificationExecutor<Branch> {
    boolean existsByEmail(String email);

    @Query("select branch.id as id, branch.name as name, branch.contactNo as contactNo, branch.email as email," +
            " branch.status as status, address.streetAddress1 as streetAddress1, address.streetAddress2 as streetAddress2," +
            " address.city as city, address.state as state, address.country as country, address.zipcode as zipcode" +
            " from Branch branch left join branch.address address where branch.id = :id")
    Optional<BranchDetailPrj> fetchById(UUID id);
}

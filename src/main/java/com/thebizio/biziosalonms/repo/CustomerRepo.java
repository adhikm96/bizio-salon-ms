package com.thebizio.biziosalonms.repo;

import com.thebizio.biziosalonms.entity.CustomerUser;
import com.thebizio.biziosalonms.enums.StatusEnum;
import com.thebizio.biziosalonms.projection.customer.CustomerDetailPrj;
import com.thebizio.biziosalonms.projection.customer.CustomerListPrj;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepo extends JpaRepository<CustomerUser,UUID> {

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    @Query("SELECT cu.id as id, cu.firstName as firstName, cu.lastName as lastName, cu.username as username," +
            "cu.email as email, cu.mobile as mobile, add.zipcode as zipcode, cu.federation as federation," +
            "cu.status as status FROM CustomerUser cu join cu.address add WHERE cu.email = :email ORDER BY cu.modified DESC")
    List<CustomerListPrj> findByEmail(String email);

    @Query("SELECT cu.id as id, cu.firstName as firstName, cu.lastName as lastName, cu.username as username," +
            "cu.email as email, cu.mobile as mobile, add.zipcode as zipcode, cu.federation as federation," +
            "cu.status as status FROM CustomerUser cu join cu.address add WHERE cu.mobile = :mobile ORDER BY cu.modified DESC")
    List<CustomerListPrj> findByMobile(String mobile);

    @Query("SELECT cu.id as id, cu.firstName as firstName, cu.lastName as lastName, cu.username as username," +
            "cu.email as email, cu.mobile as mobile, add.zipcode as zipcode, cu.federation as federation," +
            "cu.status as status FROM CustomerUser cu join cu.address add WHERE cu.status = :status AND add.zipcode = :zipcode ORDER BY cu.modified DESC")
    List<CustomerListPrj> findAllByStatusAndZipcode(StatusEnum status, String zipcode);

    @Query("SELECT cu.id as id, cu.firstName as firstName, cu.lastName as lastName, cu.username as username," +
            "cu.email as email, cu.mobile as mobile, add.zipcode as zipcode, cu.federation as federation," +
            "cu.status as status FROM CustomerUser cu join cu.address add WHERE cu.status = :status ORDER BY cu.modified DESC")
    List<CustomerListPrj> findAllByStatus(StatusEnum status);

    @Query("SELECT cu.id as id, cu.firstName as firstName, cu.lastName as lastName, cu.username as username," +
            "cu.email as email, cu.mobile as mobile, add.zipcode as zipcode, cu.federation as federation," +
            "cu.status as status FROM CustomerUser cu join cu.address add WHERE add.zipcode = :zipcode ORDER BY cu.modified DESC")
    List<CustomerListPrj> findAllByZipcode(String zipcode);

    @Query("SELECT cu.id as id, cu.firstName as firstName, cu.lastName as lastName, cu.username as username," +
            "cu.email as email, cu.mobile as mobile, add.zipcode as zipcode, cu.federation as federation," +
            "cu.status as status FROM CustomerUser cu join cu.address add ORDER BY cu.modified DESC")
    List<CustomerListPrj> findAllCustomer();

    @Query("SELECT cu.id as id, cu.firstName as firstName, cu.lastName as lastName, cu.username as username, " +
            "cu.email as email, cu.mobile as mobile, cu.gender as gender, add.streetAddress1 as streetAddress1, " +
            "add.streetAddress2 as streetAddress2, add.city as city, add.state as state, add.country as country, " +
            "add.zipcode as zipcode, cu.federation as federation, cu.status as status " +
            "FROM CustomerUser cu join cu.address add WHERE cu.id = :userId")
    Optional<CustomerDetailPrj> findCustomerById(UUID userId);
}

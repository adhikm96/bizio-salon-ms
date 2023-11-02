package com.thebizio.biziosalonms.repo;


import com.thebizio.biziosalonms.entity.User;
import com.thebizio.biziosalonms.projection.salon_user.SalonUserDetailPrj;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.UUID;

public interface SalonUserRepo extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User> {
    @Query("select user.firstName as firstName, user.lastName as lastName, user.username as username, user.email as email," +
            " user.mobile as mobile, user.bizioId as bizioId, user.empCode as empCode, user.empType as empType," +
            " user.paySchedule as paySchedule, user.designation as designation, user.status as status, user.id as id, " +
            " add.streetAddress1 as streetAddress1, add.streetAddress2 as streetAddress2, add.city as city," +
            " add.state as state, add.country as country, add.zipcode as zipcode" +
            " from User user" +
            " join user.address add " +
            " where user.id = :id")
    Optional<SalonUserDetailPrj> findByUserId(UUID id);

    boolean existsByEmpCode(String empCode);
}



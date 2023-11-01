package com.thebizio.biziosalonms.repo;


import com.thebizio.biziosalonms.entity.User;
import com.thebizio.biziosalonms.projections.salon_user.SalonUserDetailPrj;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface SalonUserRepo extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User> {

    @Query("select user.firstName as firstName, user.lastName as lastName, user.username as username, user.email as email," +
            " user.mobile as mobile, user.bizioId as bizioId, user.empCode as empCode, user.empType as empType," +
            " user.paySchedule as paySchedule, user.designation as designation, user.status as status" +
            " from User user where user.id = :id")
    SalonUserDetailPrj findByUserId(UUID id);
}


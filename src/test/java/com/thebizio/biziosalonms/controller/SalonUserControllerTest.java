package com.thebizio.biziosalonms.controller;

import com.thebizio.biziosalonms.entity.User;
import com.thebizio.biziosalonms.enums.EmpType;
import com.thebizio.biziosalonms.enums.PaySchedule;
import com.thebizio.biziosalonms.enums.StatusEnum;
import com.thebizio.biziosalonms.utils.BaseControllerTestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.hamcrest.CoreMatchers.is;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class SalonUserControllerTest extends BaseControllerTestCase {
    User salonUser1;
    User salonUser2;

    @BeforeEach
    public void beforeEach() {
        super.beforeEach();
        salonUser1 = demoEntitiesGenerator.getSalonUser(StatusEnum.ENABLED);
        salonUser2 = demoEntitiesGenerator.getSalonUser(StatusEnum.DISABLED);
    }

    @Test
    void list_all() throws Exception {
        mvc.perform(mvcReqHelper.setUp(get("/api/v1/salon-users")))
                .andExpect(jsonPath("$.length()", is(2)));

        mvc.perform(mvcReqHelper.setUp(get("/api/v1/salon-users?status="+ StatusEnum.ENABLED)))
                .andExpect(jsonPath("$.length()", is(1)))
                .andExpect(jsonPath("$[0].username", is(salonUser1.getUsername())));

        mvc.perform(mvcReqHelper.setUp(get("/api/v1/salon-users?status="+ StatusEnum.DISABLED)))
                .andExpect(jsonPath("$.length()", is(1)))
                .andExpect(jsonPath("$[0].username", is(salonUser2.getUsername())));

        mvc.perform(mvcReqHelper.setUp(get("/api/v1/salon-users?email="+ salonUser2.getEmail())))
                .andExpect(jsonPath("$.length()", is(1)))
                .andExpect(jsonPath("$[0].username", is(salonUser2.getUsername())));

        mvc.perform(mvcReqHelper.setUp(get("/api/v1/salon-users?empCode="+ salonUser2.getEmpCode())))
                .andExpect(jsonPath("$.length()", is(1)))
                .andExpect(jsonPath("$[0].username", is(salonUser2.getUsername())));

        salonUser2 = demoEntitiesGenerator.getSalonUser(StatusEnum.DISABLED, EmpType.PAYROLL_FULL_TIME);

        salonUser2 = demoEntitiesGenerator.getSalonUser(StatusEnum.DISABLED, PaySchedule.DAILY);

        mvc.perform(mvcReqHelper.setUp(get("/api/v1/salon-users?paySchedule="+ salonUser2.getPaySchedule())))
                .andExpect(jsonPath("$.length()", is(1)))
                .andExpect(jsonPath("$[0].username", is(salonUser2.getUsername())));

        mvc.perform(mvcReqHelper.setUp(get("/api/v1/salon-users?paySchedule="+ salonUser2.getPaySchedule()+"&status=" + StatusEnum.ENABLED)))
                .andExpect(jsonPath("$.length()", is(0)));

        mvc.perform(mvcReqHelper.setUp(get("/api/v1/salon-users?paySchedule="+ salonUser2.getPaySchedule()+"&status=" + StatusEnum.DISABLED)))
                .andExpect(jsonPath("$.length()", is(1)))
                .andExpect(jsonPath("$[0].username", is(salonUser2.getUsername())));

        salonUser2 = demoEntitiesGenerator.getSalonUser(demoEntitiesGenerator.getWorkSchedule());

        mvc.perform(mvcReqHelper.setUp(get("/api/v1/salon-users?workSchedule="+ salonUser2.getWorkSchedule().getId())))
                .andExpect(jsonPath("$.length()", is(1)))
                .andExpect(jsonPath("$[0].username", is(salonUser2.getUsername())));

        salonUser2 = demoEntitiesGenerator.getSalonUser(demoEntitiesGenerator.getBranch());

        mvc.perform(mvcReqHelper.setUp(get("/api/v1/salon-users?branch="+ salonUser2.getBranch().getId())))
                .andExpect(jsonPath("$.length()", is(1)))
                .andExpect(jsonPath("$[0].username", is(salonUser2.getUsername())));

        salonUser2 = demoEntitiesGenerator.getSalonUser(demoEntitiesGenerator.getBranch(), demoEntitiesGenerator.getWorkSchedule(), StatusEnum.ENABLED);

        mvc.perform(mvcReqHelper.setUp(get("/api/v1/salon-users?branch="+ salonUser2.getBranch().getId() + "&status=" + salonUser2.getStatus()+ "&workSchedule=" + salonUser2.getWorkSchedule().getId())))
                .andExpect(jsonPath("$.length()", is(1)))
                .andExpect(jsonPath("$[0].username", is(salonUser2.getUsername())));

        mvc.perform(mvcReqHelper.setUp(get("/api/v1/salon-users?branch="+ salonUser2.getBranch().getId() + "&status=" + StatusEnum.DISABLED + "&workSchedule=" + salonUser2.getWorkSchedule().getId())))
                .andExpect(jsonPath("$.length()", is(0)));
    }

    @Test
    void getTest() throws Exception {
        mvc.perform(mvcReqHelper.setUp(get("/api/v1/salon-users/" + salonUser1.getId())))
                .andExpect(jsonPath("$.username", is(salonUser1.getUsername())))
                .andExpect(jsonPath("$.firstName", is(salonUser1.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(salonUser1.getLastName())))
                .andExpect(jsonPath("$.username", is(salonUser1.getUsername())))
                .andExpect(jsonPath("$.email", is(salonUser1.getEmail())))
                .andExpect(jsonPath("$.mobile", is(salonUser1.getMobile())))
                .andExpect(jsonPath("$.bizioId", is(salonUser1.getBizioId())))
                .andExpect(jsonPath("$.empCode", is(salonUser1.getEmpCode())))
                .andExpect(jsonPath("$.empType", is(salonUser1.getEmpType())))
                .andExpect(jsonPath("$.paySchedule", is(salonUser1.getPaySchedule())))
                .andExpect(jsonPath("$.designation", is(salonUser1.getDesignation())))
                .andExpect(jsonPath("$.status", is(salonUser1.getStatus())));
    }
}

package com.thebizio.biziosalonms.controller;

import com.thebizio.biziosalonms.dto.salon_user.SalonUserUpdateDto;
import com.thebizio.biziosalonms.entity.User;
import com.thebizio.biziosalonms.enums.EmpType;
import com.thebizio.biziosalonms.enums.PaySchedule;
import com.thebizio.biziosalonms.enums.StatusEnum;
import com.thebizio.biziosalonms.service.SalonUserService;
import com.thebizio.biziosalonms.utils.BaseControllerTestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SalonUserControllerTest extends BaseControllerTestCase {
    User salonUser1;
    User salonUser2;

    @Autowired
    SalonUserService salonUserService;



    @BeforeEach
    public void beforeEach() {
        super.beforeEach();
        salonUser1 = demoEntitiesGenerator.getSalonUser(StatusEnum.ENABLED);
        salonUser2 = demoEntitiesGenerator.getSalonUser(StatusEnum.DISABLED);
    }

    @Test
    void listAllTest() throws Exception {
        mvc.perform(mvcReqHelper.setUp(get("/api/v1/salon-users"), demoEntitiesGenerator.getAdminUser()))
                .andExpect(jsonPath("$.length()", is(2)));

        mvc.perform(mvcReqHelper.setUp(get("/api/v1/salon-users?status="+ StatusEnum.ENABLED), demoEntitiesGenerator.getAdminUser()))
                .andExpect(jsonPath("$.length()", is(1)))
                .andExpect(jsonPath("$[0].username", is(salonUser1.getUsername())));

        mvc.perform(mvcReqHelper.setUp(get("/api/v1/salon-users?status="+ StatusEnum.DISABLED), demoEntitiesGenerator.getAdminUser()))
                .andExpect(jsonPath("$.length()", is(1)))
                .andExpect(jsonPath("$[0].username", is(salonUser2.getUsername())));

        mvc.perform(mvcReqHelper.setUp(get("/api/v1/salon-users?email="+ salonUser2.getEmail()), demoEntitiesGenerator.getAdminUser()))
                .andExpect(jsonPath("$.length()", is(1)))
                .andExpect(jsonPath("$[0].username", is(salonUser2.getUsername())));

        mvc.perform(mvcReqHelper.setUp(get("/api/v1/salon-users?empCode="+ salonUser2.getEmpCode()), demoEntitiesGenerator.getAdminUser()))
                .andExpect(jsonPath("$.length()", is(1)))
                .andExpect(jsonPath("$[0].username", is(salonUser2.getUsername())));

        salonUser2 = demoEntitiesGenerator.getSalonUser(StatusEnum.DISABLED, EmpType.PAYROLL_FULL_TIME);

        salonUser2 = demoEntitiesGenerator.getSalonUser(StatusEnum.DISABLED, PaySchedule.DAILY);

        mvc.perform(mvcReqHelper.setUp(get("/api/v1/salon-users?paySchedule="+ salonUser2.getPaySchedule()), demoEntitiesGenerator.getAdminUser()))
                .andExpect(jsonPath("$.length()", is(1)))
                .andExpect(jsonPath("$[0].username", is(salonUser2.getUsername())));

        mvc.perform(mvcReqHelper.setUp(get("/api/v1/salon-users?paySchedule="+ salonUser2.getPaySchedule()+"&status=" + StatusEnum.ENABLED), demoEntitiesGenerator.getAdminUser()))
                .andExpect(jsonPath("$.length()", is(0)));

        mvc.perform(mvcReqHelper.setUp(get("/api/v1/salon-users?paySchedule="+ salonUser2.getPaySchedule()+"&status=" + StatusEnum.DISABLED), demoEntitiesGenerator.getAdminUser()))
                .andExpect(jsonPath("$.length()", is(1)))
                .andExpect(jsonPath("$[0].username", is(salonUser2.getUsername())));

        salonUser2 = demoEntitiesGenerator.getSalonUser(demoEntitiesGenerator.getWorkSchedule());

        mvc.perform(mvcReqHelper.setUp(get("/api/v1/salon-users?workSchedule="+ salonUser2.getWorkSchedule().getId()), demoEntitiesGenerator.getAdminUser()))
                .andExpect(jsonPath("$.length()", is(1)))
                .andExpect(jsonPath("$[0].username", is(salonUser2.getUsername())));

        salonUser2 = demoEntitiesGenerator.getSalonUser(demoEntitiesGenerator.getBranch());

        mvc.perform(mvcReqHelper.setUp(get("/api/v1/salon-users?branch="+ salonUser2.getBranch().getId()), demoEntitiesGenerator.getAdminUser()))
                .andExpect(jsonPath("$.length()", is(1)))
                .andExpect(jsonPath("$[0].username", is(salonUser2.getUsername())));

        salonUser2 = demoEntitiesGenerator.getSalonUser(demoEntitiesGenerator.getBranch(), demoEntitiesGenerator.getWorkSchedule(), StatusEnum.ENABLED);

        mvc.perform(mvcReqHelper.setUp(get("/api/v1/salon-users?branch="+ salonUser2.getBranch().getId() + "&status=" + salonUser2.getStatus()+ "&workSchedule=" + salonUser2.getWorkSchedule().getId()), demoEntitiesGenerator.getAdminUser()))
                .andExpect(jsonPath("$.length()", is(1)))
                .andExpect(jsonPath("$[0].username", is(salonUser2.getUsername())));

        mvc.perform(mvcReqHelper.setUp(get("/api/v1/salon-users?branch="+ salonUser2.getBranch().getId() + "&status=" + StatusEnum.DISABLED + "&workSchedule=" + salonUser2.getWorkSchedule().getId()), demoEntitiesGenerator.getAdminUser()))
                .andExpect(jsonPath("$.length()", is(0)));
    }

    @Test
    void getTest() throws Exception {
        mvc.perform(mvcReqHelper.setUp(get("/api/v1/salon-users/" + salonUser1.getId()), demoEntitiesGenerator.getAdminUser()))
                .andExpect(jsonPath("$.username", is(salonUser1.getUsername())))
                .andExpect(jsonPath("$.firstName", is(salonUser1.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(salonUser1.getLastName())))
                .andExpect(jsonPath("$.username", is(salonUser1.getUsername())))
                .andExpect(jsonPath("$.email", is(salonUser1.getEmail())))
                .andExpect(jsonPath("$.mobile", is(salonUser1.getMobile())))
                .andExpect(jsonPath("$.bizioId", is(salonUser1.getBizioId())))
                .andExpect(jsonPath("$.empCode", is(salonUser1.getEmpCode())))
                .andExpect(jsonPath("$.empType", is(salonUser1.getEmpType().toString())))
                .andExpect(jsonPath("$.paySchedule", is(salonUser1.getPaySchedule().toString())))
                .andExpect(jsonPath("$.designation", is(salonUser1.getDesignation())))
                .andExpect(jsonPath("$.status", is(salonUser1.getStatus().toString())));
    }

    @Test
    void updateTest() throws Exception {

        salonUser1 = demoEntitiesGenerator.getSalonUser(demoEntitiesGenerator.getBranch(), demoEntitiesGenerator.getWorkSchedule(), StatusEnum.ENABLED);

        UUID oldBranchId = salonUser1.getBranch().getId();
        UUID oldWSId = salonUser1.getWorkSchedule().getId();

        SalonUserUpdateDto salonUserUpdateDto = new SalonUserUpdateDto();

        salonUserUpdateDto.setDesignation("Designer");
        salonUserUpdateDto.setEmpCode("EMp-ABC");
        salonUserUpdateDto.setPaySchedule(PaySchedule.DAILY);
        salonUserUpdateDto.setEmpType(EmpType.TEMPORARY);

        mvc.perform(mvcReqHelper.setUp(put("/api/v1/salon-users/" + salonUser1.getId()), salonUserUpdateDto, demoEntitiesGenerator.getAdminUser()))
                .andExpect(status().isOk());

        salonUser1 = salonUserService.findById(salonUser1.getId());

        assertEquals(salonUserUpdateDto.getDesignation(), salonUser1.getDesignation());
        assertEquals(salonUserUpdateDto.getEmpCode(), salonUser1.getEmpCode());
        assertEquals(salonUserUpdateDto.getPaySchedule(), salonUser1.getPaySchedule());
        assertEquals(salonUserUpdateDto.getEmpType(), salonUser1.getEmpType());
        assertEquals(oldBranchId, salonUser1.getBranch().getId());
        assertEquals(oldWSId, salonUser1.getWorkSchedule().getId());

        salonUserUpdateDto.setBranchId(demoEntitiesGenerator.getBranch().getId());

        mvc.perform(mvcReqHelper.setUp(put("/api/v1/salon-users/" + salonUser1.getId()), salonUserUpdateDto, demoEntitiesGenerator.getAdminUser()))
                .andExpect(status().isOk());

        salonUser1 = salonUserService.findById(salonUser1.getId());

        assertEquals(salonUserUpdateDto.getDesignation(), salonUser1.getDesignation());
        assertEquals(salonUserUpdateDto.getEmpCode(), salonUser1.getEmpCode());
        assertEquals(salonUserUpdateDto.getPaySchedule(), salonUser1.getPaySchedule());
        assertEquals(salonUserUpdateDto.getEmpType(), salonUser1.getEmpType());
        assertEquals(salonUserUpdateDto.getBranchId(), salonUser1.getBranch().getId());
        assertEquals(oldWSId, salonUser1.getWorkSchedule().getId());

        salonUserUpdateDto.setWorkScheduleId(demoEntitiesGenerator.getWorkSchedule().getId());

        mvc.perform(mvcReqHelper.setUp(put("/api/v1/salon-users/" + salonUser1.getId()), salonUserUpdateDto, demoEntitiesGenerator.getAdminUser()))
                .andExpect(status().isOk());

        salonUser1 = salonUserService.findById(salonUser1.getId());

        assertEquals(salonUserUpdateDto.getDesignation(), salonUser1.getDesignation());
        assertEquals(salonUserUpdateDto.getEmpCode(), salonUser1.getEmpCode());
        assertEquals(salonUserUpdateDto.getPaySchedule(), salonUser1.getPaySchedule());
        assertEquals(salonUserUpdateDto.getEmpType(), salonUser1.getEmpType());
        assertEquals(salonUserUpdateDto.getBranchId(), salonUser1.getBranch().getId());
        assertEquals(salonUserUpdateDto.getWorkScheduleId(), salonUser1.getWorkSchedule().getId());
    }

    @Test
    void statusChangeTest() throws Exception {
        salonUser1 = demoEntitiesGenerator.getSalonUser(demoEntitiesGenerator.getBranch(), demoEntitiesGenerator.getWorkSchedule(), StatusEnum.ENABLED);

        mvc.perform(mvcReqHelper.setUp(post("/api/v1/salon-users/disable/" + salonUser1.getId()), demoEntitiesGenerator.getAdminUser()))
                .andExpect(jsonPath("$.message", is("OK")));

        salonUser1 = salonUserService.findById(salonUser1.getId());

        mvc.perform(mvcReqHelper.setUp(post("/api/v1/salon-users/disable/" + salonUser1.getId()), demoEntitiesGenerator.getAdminUser()))
                .andExpect(status().isBadRequest());

        assertEquals(StatusEnum.DISABLED, salonUserService.findById(salonUser1.getId()).getStatus());

        mvc.perform(mvcReqHelper.setUp(post("/api/v1/salon-users/enable/" + salonUser1.getId()), demoEntitiesGenerator.getAdminUser()))
                .andExpect(jsonPath("$.message", is("OK")));

        salonUser1 = salonUserService.findById(salonUser1.getId());

        assertEquals(StatusEnum.ENABLED, salonUserService.findById(salonUser1.getId()).getStatus());

        mvc.perform(mvcReqHelper.setUp(post("/api/v1/salon-users/enable/" + salonUser1.getId()), demoEntitiesGenerator.getAdminUser()))
                .andExpect(status().isBadRequest());
    }
}

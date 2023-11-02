package com.thebizio.biziosalonms.controller;

import com.thebizio.biziosalonms.dto.appointment.CreateAppointmentDto;
import com.thebizio.biziosalonms.dto.salon_user.SalonUserUpdateDto;
import com.thebizio.biziosalonms.entity.*;
import com.thebizio.biziosalonms.enums.AppointmentStatus;
import com.thebizio.biziosalonms.enums.EmpType;
import com.thebizio.biziosalonms.enums.PaySchedule;
import com.thebizio.biziosalonms.enums.StatusEnum;
import com.thebizio.biziosalonms.repo.ItemRepo;
import com.thebizio.biziosalonms.service.SalonUserService;
import com.thebizio.biziosalonms.utils.BaseControllerTestCase;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AppointmentControllerTest extends BaseControllerTestCase {
    @Autowired
    SalonUserService salonUserService;

    Appointment appointment1;
    Appointment appointment2;

    @BeforeEach
    public void beforeEach() {
        super.beforeEach();
        appointment1 = demoEntitiesGenerator.getAppointment(AppointmentStatus.SCHEDULED);
        appointment2 = demoEntitiesGenerator.getAppointment(AppointmentStatus.BOOKED);
    }

    @Test
    void listAllTest() throws Exception {
        mvc.perform(mvcReqHelper.setUp(get("/api/v1/appointments"), demoEntitiesGenerator.getAdminUser()))
                .andExpect(jsonPath("$.length()", is(2)));

        mvc.perform(mvcReqHelper.setUp(get("/api/v1/appointments?status="+AppointmentStatus.BOOKED), demoEntitiesGenerator.getAdminUser()))
                .andExpect(jsonPath("$.length()", is(1)));

        mvc.perform(mvcReqHelper.setUp(get("/api/v1/appointments?customer="+appointment2.getCustomerUser().getId()), demoEntitiesGenerator.getAdminUser()))
                .andExpect(jsonPath("$.length()", is(1)));

        mvc.perform(mvcReqHelper.setUp(get("/api/v1/appointments?branch="+appointment2.getBranch().getId()), demoEntitiesGenerator.getAdminUser()))
                .andExpect(jsonPath("$.length()", is(1)));

        mvc.perform(mvcReqHelper.setUp(get("/api/v1/appointments?assignedTo="+appointment2.getAssignedTo().getId()), demoEntitiesGenerator.getAdminUser()))
                .andExpect(jsonPath("$.length()", is(1)));
    }


    @Test
    void createAppointmentTest() throws Exception {
        CustomerUser customerUser = demoEntitiesGenerator.getCustomer();
        Branch branch = demoEntitiesGenerator.getBranch();
        Item item = demoEntitiesGenerator.getItem();
        CreateAppointmentDto dto = new CreateAppointmentDto();
        dto.setCustomerId(customerUser.getId());
        dto.setBranchId(branch.getId());
        dto.setDate(LocalDate.now().plusDays(1));
        dto.setTime(LocalTime.now().minusHours(2));
        dto.setNotes("Notes");
        dto.setProductAndServices(Collections.singletonList(item.getId()));


        mvc.perform(mvcReqHelper.setUp(post("/api/v1/appointments/create"),dto, demoEntitiesGenerator.getAdminUser()))
                .andExpect(jsonPath("$.customer.id", is(customerUser.getId().toString())))
                .andExpect(jsonPath("$.status", is(AppointmentStatus.BOOKED.toString())))
                .andExpect(jsonPath("$.branch.id", is(branch.getId().toString())));
    }


}

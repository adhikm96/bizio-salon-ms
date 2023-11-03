package com.thebizio.biziosalonms.controller;

import com.thebizio.biziosalonms.dto.work_schedule.WorkScheduleCreateUpdateDto;
import com.thebizio.biziosalonms.dto.work_schedule.WsItem;
import com.thebizio.biziosalonms.entity.Branch;
import com.thebizio.biziosalonms.entity.WorkSchedule;
import com.thebizio.biziosalonms.enums.StatusEnum;
import com.thebizio.biziosalonms.repo.WsItemRepo;
import com.thebizio.biziosalonms.service.WorkScheduleService;
import com.thebizio.biziosalonms.utils.BaseControllerTestCase;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class WorkScheduleControllerTestCase extends BaseControllerTestCase {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    WsItemRepo wsItemRepo;

    @Autowired
    WorkScheduleService workScheduleService;

    @Test
    void createTest() throws Exception {

        WsItem item = modelMapper.map(demoEntitiesGenerator.getWorkScheduleItem(), WsItem.class);

        WorkScheduleCreateUpdateDto dto = new WorkScheduleCreateUpdateDto();
        dto.setName("name");
        dto.setItems(Collections.singletonList(item));

        mvc.perform(mvcReqHelper.setUp(post("/api/v1/works-schedules"),dto, demoEntitiesGenerator.getAdminUser()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(StatusEnum.ENABLED.toString())))
                .andExpect(jsonPath("$.name", is(dto.getName())))
                .andExpect(jsonPath("$.items.[0].day", is(item.getDay().toString())))
                .andExpect(jsonPath("$.items.[0].startTime", is(item.getStartTime().toString())))
                .andExpect(jsonPath("$.items.[0].breakStartTime", is(item.getBreakStartTime().toString())))
                .andExpect(jsonPath("$.items.[0].breakEndTime", is(item.getBreakEndTime().toString())))
                .andExpect(jsonPath("$.items.[0].endTime", is(item.getEndTime().toString())));
    }


    @Test
    void updateTest() throws Exception {

        WorkSchedule ws = demoEntitiesGenerator.getWorkSchedule();

        WsItem item = modelMapper.map(demoEntitiesGenerator.getWorkScheduleItem(), WsItem.class);

        WorkScheduleCreateUpdateDto dto = new WorkScheduleCreateUpdateDto();
        dto.setName("updated name");
        dto.setItems(Collections.singletonList(item));

        mvc.perform(mvcReqHelper.setUp(put("/api/v1/works-schedules/" + ws.getId()),dto, demoEntitiesGenerator.getAdminUser()))
                .andExpect(status().isOk());

        ws = workScheduleService.findById(ws.getId());

        assertEquals(1, ws.getWorkScheduleItems().size());
        assertEquals(dto.getName(), ws.getName());

        assertEquals(ws.getWorkScheduleItems().get(0).getDay(), item.getDay());
        assertEquals(ws.getWorkScheduleItems().get(0).getStartTime(), item.getStartTime());
        assertEquals(ws.getWorkScheduleItems().get(0).getBreakStartTime(), item.getBreakStartTime());
        assertEquals(ws.getWorkScheduleItems().get(0).getBreakEndTime(), item.getBreakEndTime());
        assertEquals(ws.getWorkScheduleItems().get(0).getEndTime(), item.getEndTime());

        dto.setItems(Arrays.asList(
                modelMapper.map(demoEntitiesGenerator.getWorkScheduleItem(), WsItem.class),
                modelMapper.map(demoEntitiesGenerator.getWorkScheduleItem(), WsItem.class),
                modelMapper.map(demoEntitiesGenerator.getWorkScheduleItem(), WsItem.class)
        ));

        mvc.perform(mvcReqHelper.setUp(put("/api/v1/works-schedules/" + ws.getId()),dto, demoEntitiesGenerator.getAdminUser()))
                .andExpect(status().isOk());

        ws = workScheduleService.findById(ws.getId());

        assertEquals(3, ws.getWorkScheduleItems().size());
    }

    @Test
    void getTest() throws Exception {

        WorkSchedule workSchedule = demoEntitiesGenerator.getWorkSchedule();

        mvc.perform(mvcReqHelper.setUp(get("/api/v1/works-schedules/" + workSchedule.getId()), demoEntitiesGenerator.getAdminUser()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(workSchedule.getStatus().toString())))
                .andExpect(jsonPath("$.name", is(workSchedule.getName())))
                .andExpect(jsonPath("$.items[0].day", is(workSchedule.getWorkScheduleItems().get(0).getDay().toString())))
                .andExpect(jsonPath("$.items[0].startTime", is(workSchedule.getWorkScheduleItems().get(0).getStartTime().toString())))
                .andExpect(jsonPath("$.items[0].breakStartTime", is(workSchedule.getWorkScheduleItems().get(0).getBreakStartTime().toString())))
                .andExpect(jsonPath("$.items[0].breakEndTime", is(workSchedule.getWorkScheduleItems().get(0).getBreakEndTime().toString())))
                .andExpect(jsonPath("$.items[0].endTime", is(workSchedule.getWorkScheduleItems().get(0).getEndTime().toString())));
    }

    @Test
    void listTest() throws Exception {

        WorkSchedule ws = demoEntitiesGenerator.getWorkSchedule();

        mvc.perform(mvcReqHelper.setUp(get("/api/v1/works-schedules"), demoEntitiesGenerator.getAdminUser()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].status", is(ws.getStatus().toString())))
                .andExpect(jsonPath("$[0].name", is(ws.getName())));

        ws = demoEntitiesGenerator.getWorkSchedule(StatusEnum.DISABLED);

        mvc.perform(mvcReqHelper.setUp(get("/api/v1/works-schedules?status=" + ws.getStatus()), demoEntitiesGenerator.getAdminUser()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].status", is(ws.getStatus().toString())))
                .andExpect(jsonPath("$[0].name", is(ws.getName())));

        Branch branch = demoEntitiesGenerator.getBranch();

        ws = demoEntitiesGenerator.getWorkSchedule(branch);

        mvc.perform(mvcReqHelper.setUp(get("/api/v1/works-schedules?branch=" + branch.getName()), demoEntitiesGenerator.getAdminUser()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].status", is(ws.getStatus().toString())))
                .andExpect(jsonPath("$[0].name", is(ws.getName())));

        ws = demoEntitiesGenerator.getWorkSchedule();

        mvc.perform(mvcReqHelper.setUp(get("/api/v1/works-schedules?name=" + ws.getName()), demoEntitiesGenerator.getAdminUser()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].status", is(ws.getStatus().toString())))
                .andExpect(jsonPath("$[0].name", is(ws.getName())));
    }

    @Test
    void statusChangeTest() throws Exception {
        WorkSchedule ws1 = demoEntitiesGenerator.getWorkSchedule(StatusEnum.ENABLED);

        mvc.perform(mvcReqHelper.setUp(post("/api/v1/works-schedules/disable/" + ws1.getId()), demoEntitiesGenerator.getAdminUser()))
                .andExpect(jsonPath("$.message", is("OK")));

        ws1 = workScheduleService.findById(ws1.getId());

        mvc.perform(mvcReqHelper.setUp(post("/api/v1/works-schedules/disable/" + ws1.getId()), demoEntitiesGenerator.getAdminUser()))
                .andExpect(status().isBadRequest());

        assertEquals(StatusEnum.DISABLED, workScheduleService.findById(ws1.getId()).getStatus());

        mvc.perform(mvcReqHelper.setUp(post("/api/v1/works-schedules/enable/" + ws1.getId()), demoEntitiesGenerator.getAdminUser()))
                .andExpect(jsonPath("$.message", is("OK")));

        ws1 = workScheduleService.findById(ws1.getId());

        assertEquals(StatusEnum.ENABLED, workScheduleService.findById(ws1.getId()).getStatus());

        mvc.perform(mvcReqHelper.setUp(post("/api/v1/works-schedules/enable/" + ws1.getId()), demoEntitiesGenerator.getAdminUser()))
                .andExpect(status().isBadRequest());
    }
}

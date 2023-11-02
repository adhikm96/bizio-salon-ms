package com.thebizio.biziosalonms.controller;

import com.thebizio.biziosalonms.dto.branch.BranchCreateUpdateDto;
import com.thebizio.biziosalonms.entity.Branch;
import com.thebizio.biziosalonms.entity.Company;
import com.thebizio.biziosalonms.entity.WorkSchedule;
import com.thebizio.biziosalonms.enums.BranchStatusEnum;
import com.thebizio.biziosalonms.service.BranchService;
import com.thebizio.biziosalonms.utils.BaseControllerTestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BranchControllerTest extends BaseControllerTestCase {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    BranchService branchService;


    @BeforeEach
    public void beforeEach() {
        super.beforeEach();
    }

    @Test
    void createTest() throws Exception {

        Company company = demoEntitiesGenerator.getCompany();

        WorkSchedule workSchedule = demoEntitiesGenerator.getWorkSchedule();

        BranchCreateUpdateDto dto = new BranchCreateUpdateDto();
        dto.setEmail(demoEntitiesGenerator.fakeEmail());
        dto.setStreetAddress1("Address Line1");
        dto.setStreetAddress2("Address Line1");
        dto.setCity("St.Albert");
        dto.setZipcode("T8N");
        dto.setState("Alberta");
        dto.setCountry("Canada");
        dto.setName("Branch-A");
        dto.setCompanyId(company.getId());
        dto.setWorkScheduleId(workSchedule.getId());

        mvc.perform(mvcReqHelper.setUp(post("/api/v1/branches"),dto, demoEntitiesGenerator.getAdminUser()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(notNullValue())))
                .andExpect(jsonPath("$.name", is(dto.getName())))
                .andExpect(jsonPath("$.contactNo", is(dto.getContactNo())))
                .andExpect(jsonPath("$.email", is(dto.getEmail())))
                .andExpect(jsonPath("$.status", is(BranchStatusEnum.OPENED.toString())))
                .andExpect(jsonPath("$.streetAddress1", is(dto.getStreetAddress1())))
                .andExpect(jsonPath("$.streetAddress2", is(dto.getStreetAddress2())))
                .andExpect(jsonPath("$.city", is(dto.getCity())))
                .andExpect(jsonPath("$.state", is(dto.getState())))
                .andExpect(jsonPath("$.country", is(dto.getCountry())))
                .andExpect(jsonPath("$.zipcode", is(dto.getZipcode())));

        mvc.perform(mvcReqHelper.setUp(post("/api/v1/branches"),dto, demoEntitiesGenerator.getAdminUser()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("email already exists")));
    }

    @Test
    void getTest() throws Exception {
        Branch branch = demoEntitiesGenerator.getBranch();

        mvc.perform(mvcReqHelper.setUp(get("/api/v1/branches/" + branch.getId()), demoEntitiesGenerator.getAdminUser()))
                .andExpect(jsonPath("$.id", is(branch.getId().toString())))
                .andExpect(jsonPath("$.name", is(branch.getName())))
                .andExpect(jsonPath("$.contactNo", is(branch.getContactNo())))
                .andExpect(jsonPath("$.email", is(branch.getEmail())))
                .andExpect(jsonPath("$.status", is(branch.getStatus().toString())))
                .andExpect(jsonPath("$.streetAddress1", is(branch.getAddress().getStreetAddress1())))
                .andExpect(jsonPath("$.streetAddress2", is(branch.getAddress().getStreetAddress2())))
                .andExpect(jsonPath("$.city", is(branch.getAddress().getCity())))
                .andExpect(jsonPath("$.state", is(branch.getAddress().getState())))
                .andExpect(jsonPath("$.country", is(branch.getAddress().getCountry())))
                .andExpect(jsonPath("$.zipcode", is(branch.getAddress().getZipcode())));
    }

    @Test
    void getAllTestWithFilter() throws Exception {

        Branch branch = demoEntitiesGenerator.getBranch();

        mvc.perform(mvcReqHelper.setUp(get("/api/v1/branches"), demoEntitiesGenerator.getAdminUser()))
                .andExpect(jsonPath("$[0].id", is(branch.getId().toString())))
                .andExpect(jsonPath("$[0].name", is(branch.getName())))
                .andExpect(jsonPath("$[0].contactNo", is(branch.getContactNo())))
                .andExpect(jsonPath("$[0].email", is(branch.getEmail())))
                .andExpect(jsonPath("$[0].status", is(branch.getStatus().toString())))
                .andExpect(jsonPath("$[0].zipcode", is(branch.getAddress().getZipcode())));

        Branch branch2 = demoEntitiesGenerator.getBranch(BranchStatusEnum.PERMANENTLY_CLOSED);

        mvc.perform(mvcReqHelper.setUp(get("/api/v1/branches?status=" + branch2.getStatus()), demoEntitiesGenerator.getAdminUser()))
                .andExpect(jsonPath("$[0].id", is(branch2.getId().toString())))
                .andExpect(jsonPath("$[0].name", is(branch2.getName())))
                .andExpect(jsonPath("$[0].contactNo", is(branch2.getContactNo())))
                .andExpect(jsonPath("$[0].email", is(branch2.getEmail())))
                .andExpect(jsonPath("$[0].status", is(branch2.getStatus().toString())))
                .andExpect(jsonPath("$[0].zipcode", is(branch2.getAddress().getZipcode())));

        Branch branch3 = demoEntitiesGenerator.getBranchWithZip("!23456");

        mvc.perform(mvcReqHelper.setUp(get("/api/v1/branches?zipcode=" + branch3.getAddress().getZipcode()), demoEntitiesGenerator.getAdminUser()))
                .andExpect(jsonPath("$[0].id", is(branch3.getId().toString())))
                .andExpect(jsonPath("$[0].name", is(branch3.getName())))
                .andExpect(jsonPath("$[0].contactNo", is(branch3.getContactNo())))
                .andExpect(jsonPath("$[0].email", is(branch3.getEmail())))
                .andExpect(jsonPath("$[0].status", is(branch3.getStatus().toString())))
                .andExpect(jsonPath("$[0].zipcode", is(branch3.getAddress().getZipcode())));

        Branch branch4 = demoEntitiesGenerator.getBranchWithEmail(demoEntitiesGenerator.fakeEmail());

        mvc.perform(mvcReqHelper.setUp(get("/api/v1/branches?email=" + branch4.getEmail()), demoEntitiesGenerator.getAdminUser()))
                .andExpect(jsonPath("$[0].id", is(branch4.getId().toString())))
                .andExpect(jsonPath("$[0].name", is(branch4.getName())))
                .andExpect(jsonPath("$[0].contactNo", is(branch4.getContactNo())))
                .andExpect(jsonPath("$[0].email", is(branch4.getEmail())))
                .andExpect(jsonPath("$[0].status", is(branch4.getStatus().toString())))
                .andExpect(jsonPath("$[0].zipcode", is(branch4.getAddress().getZipcode())));
    }

    @Test
    void updateTest() throws Exception {
        Branch branch = demoEntitiesGenerator.getBranch();

        Company company = demoEntitiesGenerator.getCompany();
        WorkSchedule workSchedule = demoEntitiesGenerator.getWorkSchedule();

        BranchCreateUpdateDto dto = new BranchCreateUpdateDto();
        dto.setEmail(demoEntitiesGenerator.fakeEmail());
        dto.setStreetAddress1("Address Line1");
        dto.setStreetAddress2("Address Line2");
        dto.setCity("St.Albert");
        dto.setZipcode("T8N");
        dto.setState("Alberta");
        dto.setCountry("Canada");
        dto.setName("Branch-A");
        dto.setCompanyId(company.getId());
        dto.setWorkScheduleId(workSchedule.getId());

        mvc.perform(mvcReqHelper.setUp(put("/api/v1/branches/" + branch.getId()), dto , demoEntitiesGenerator.getAdminUser()))
                .andExpect(status().isOk());

        branch = branchService.findById(branch.getId());

        assertEquals(branch.getName(), dto.getName());

        assertEquals(branch.getContactNo(), dto.getContactNo());
        assertEquals(branch.getEmail(), dto.getEmail());


        assertEquals(branch.getAddress().getStreetAddress1(), dto.getStreetAddress1());
        assertEquals(branch.getAddress().getStreetAddress2(), dto.getStreetAddress2());
        assertEquals(branch.getAddress().getCity(), dto.getCity());
        assertEquals(branch.getAddress().getState(), dto.getState());
        assertEquals(branch.getAddress().getCountry(), dto.getCountry());
        assertEquals(branch.getAddress().getZipcode(), dto.getZipcode());

        assertEquals(branch.getWorkSchedule().getId(), workSchedule.getId());
        assertEquals(branch.getCompany().getId(), company.getId());

        Branch branch2 = demoEntitiesGenerator.getBranch();

        dto.setEmail(branch2.getEmail());

        mvc.perform(mvcReqHelper.setUp(put("/api/v1/branches/" + branch.getId()), dto , demoEntitiesGenerator.getAdminUser()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("email already exists")));
    }

    @Test
    void statusTest() throws Exception {
        Branch branch = demoEntitiesGenerator.getBranch(BranchStatusEnum.OPENED);

        mvc.perform(mvcReqHelper.setUp(post("/api/v1/branches/" + branch.getId() + "/" + BranchStatusEnum.CLOSED), demoEntitiesGenerator.getAdminUser()))
                .andExpect(jsonPath("$.message", is("OK")));

        branch = branchService.findById(branch.getId());

        assertEquals(branch.getStatus(), BranchStatusEnum.CLOSED);

        mvc.perform(mvcReqHelper.setUp(post("/api/v1/branches/" + branch.getId() + "/" + BranchStatusEnum.PERMANENTLY_CLOSED), demoEntitiesGenerator.getAdminUser()))
                .andExpect(jsonPath("$.message", is("OK")));

        branch = branchService.findById(branch.getId());

        assertEquals(branch.getStatus(), BranchStatusEnum.PERMANENTLY_CLOSED);

        mvc.perform(mvcReqHelper.setUp(post("/api/v1/branches/" + branch.getId() + "/" + BranchStatusEnum.OPENED), demoEntitiesGenerator.getAdminUser()))
                .andExpect(jsonPath("$.message", is("OK")));

        branch = branchService.findById(branch.getId());

        assertEquals(branch.getStatus(), BranchStatusEnum.OPENED);

        mvc.perform(mvcReqHelper.setUp(post("/api/v1/branches/" + branch.getId() + "/" + BranchStatusEnum.OPENED), demoEntitiesGenerator.getAdminUser()))
                .andExpect(jsonPath("$.message", is("Branch status already " + BranchStatusEnum.OPENED.toString().toLowerCase())));
    }
}

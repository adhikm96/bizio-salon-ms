package com.thebizio.biziosalonms.controller;

import com.thebizio.biziosalonms.dto.company.CompanyUpdateDto;
import com.thebizio.biziosalonms.entity.Company;
import com.thebizio.biziosalonms.service.CompanyService;
import com.thebizio.biziosalonms.utils.BaseControllerTestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CompanyControllerTest extends BaseControllerTestCase {

    Company company1;
    Company company2;

    @Autowired
    CompanyService companyService;

    @BeforeEach
    public void beforeEach() {
        super.beforeEach();
    }

    @Test
    void listTest() throws Exception {
        company1 = demoEntitiesGenerator.getCompany();

        mvc.perform(mvcReqHelper.setUp(get("/api/v1/companies"), demoEntitiesGenerator.getAdminUser()))
                .andExpect(jsonPath("$.length()", is(1)))
                .andExpect(jsonPath("$[0].id", is(company1.getId().toString())))
                .andExpect(jsonPath("$[0].name", is(company1.getName())))
                .andExpect(jsonPath("$[0].streetAddress1", is(company1.getStreetAddress1())))
                .andExpect(jsonPath("$[0].streetAddress2", is(company1.getStreetAddress2())))
                .andExpect(jsonPath("$[0].city", is(company1.getCity())))
                .andExpect(jsonPath("$[0].state", is(company1.getState())))
                .andExpect(jsonPath("$[0].country", is(company1.getCountry())))
                .andExpect(jsonPath("$[0].zipcode", is(company1.getZipcode())))
                .andExpect(jsonPath("$[0].contactNo", is(company1.getContactNo())))
                .andExpect(jsonPath("$[0].email", is(company1.getEmail())));
    }

    @Test
    void getTest() throws Exception {
        company1 = demoEntitiesGenerator.getCompany();

        mvc.perform(mvcReqHelper.setUp(get("/api/v1/companies/" + company1.getId()), demoEntitiesGenerator.getAdminUser()))
                .andExpect(jsonPath("$.id", is(company1.getId().toString())))
                .andExpect(jsonPath("$.name", is(company1.getName())))
                .andExpect(jsonPath("$.streetAddress1", is(company1.getStreetAddress1())))
                .andExpect(jsonPath("$.streetAddress2", is(company1.getStreetAddress2())))
                .andExpect(jsonPath("$.city", is(company1.getCity())))
                .andExpect(jsonPath("$.state", is(company1.getState())))
                .andExpect(jsonPath("$.country", is(company1.getCountry())))
                .andExpect(jsonPath("$.zipcode", is(company1.getZipcode())))
                .andExpect(jsonPath("$.contactNo", is(company1.getContactNo())))
                .andExpect(jsonPath("$.email", is(company1.getEmail())));

        mvc.perform(mvcReqHelper.setUp(get("/api/v1/companies/" + UUID.randomUUID()), demoEntitiesGenerator.getAdminUser()))
                .andExpect(jsonPath("$.message", is("company not found")));
    }

    @Test
    void updateTest() throws Exception {

        company1 = demoEntitiesGenerator.getCompany();

        CompanyUpdateDto updateDto = new CompanyUpdateDto();

        updateDto.setEmail(demoEntitiesGenerator.fakeEmail());
        updateDto.setCity("Mumbai");
        updateDto.setStreetAddress1("str1");
        updateDto.setStreetAddress2("str2");
        updateDto.setCountry("INDIA");
        updateDto.setState("MH");
        updateDto.setZipcode("123456");
        updateDto.setName("Company-A");

        mvc.perform(mvcReqHelper.setUp(put("/api/v1/companies/" + company1.getId()), updateDto, demoEntitiesGenerator.getAdminUser()))
                .andExpect(status().isOk());

        company1 = companyService.findById(company1.getId());

        assertEquals(updateDto.getEmail(), company1.getEmail());
        assertEquals(updateDto.getCity(), company1.getCity());
        assertEquals(updateDto.getStreetAddress1(), company1.getStreetAddress1());
        assertEquals(updateDto.getStreetAddress2(), company1.getStreetAddress2());
        assertEquals(updateDto.getCountry(), company1.getCountry());
        assertEquals(updateDto.getState(), company1.getState());
        assertEquals(updateDto.getZipcode(), company1.getZipcode());
        assertEquals(updateDto.getName(), company1.getName());

        company2 = demoEntitiesGenerator.getCompany();

        updateDto.setEmail(company2.getEmail());

        mvc.perform(mvcReqHelper.setUp(put("/api/v1/companies/" + company1.getId()), updateDto, demoEntitiesGenerator.getAdminUser()))
                .andExpect(jsonPath("$.message", is("email already exists")));
    }
}

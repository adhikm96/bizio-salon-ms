package com.thebizio.biziosalonms.controller;

import com.thebizio.biziosalonms.dto.tax_schedule.CreateUpdateTaxScheduleDto;
import com.thebizio.biziosalonms.dto.tax_schedule_item.CreateUpdateTaxScheduleItemDto;
import com.thebizio.biziosalonms.entity.*;
import com.thebizio.biziosalonms.enums.StatusEnum;
import com.thebizio.biziosalonms.enums.TaxChargeTypeEnum;
import com.thebizio.biziosalonms.utils.BaseControllerTestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class TaxScheduleControllerTest extends BaseControllerTestCase {

    @BeforeEach
    public void beforeEach() {
        super.beforeEach();
    }

    @Test
    void createTaxScheduleTest() throws Exception {
        TaxHead taxHead = demoEntitiesGenerator.getTaxHead();
        Branch branch = demoEntitiesGenerator.getBranch();
        CreateUpdateTaxScheduleDto dto = new CreateUpdateTaxScheduleDto();
        dto.setName("Tax schedule");
        dto.setBranchId(branch.getId());

        CreateUpdateTaxScheduleItemDto tsiDto = new CreateUpdateTaxScheduleItemDto();
        tsiDto.setTaxChargeType(TaxChargeTypeEnum.PERCENT);
        tsiDto.setValue(10);
        tsiDto.setTaxHeadId(taxHead.getId());

        dto.setTaxScheduleItems(Collections.singletonList(tsiDto));

        mvc.perform(mvcReqHelper.setUp(post("/api/v1/tax-schedules"),dto, demoEntitiesGenerator.getAdminUser()))
                .andExpect(jsonPath("$.status", is(StatusEnum.ENABLED.toString())))
                .andExpect(jsonPath("$.name", is(dto.getName())))
                .andExpect(jsonPath("$.branch.id", is(branch.getId().toString())))
                .andExpect(jsonPath("$.taxScheduleItems.length()", is(1)));
    }


}

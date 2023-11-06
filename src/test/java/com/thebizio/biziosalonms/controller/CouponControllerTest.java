package com.thebizio.biziosalonms.controller;

import com.thebizio.biziosalonms.dto.coupon.CouponCreateDto;
import com.thebizio.biziosalonms.dto.coupon.CouponUpdateDto;
import com.thebizio.biziosalonms.entity.Coupon;
import com.thebizio.biziosalonms.enums.CouponTypeEnum;
import com.thebizio.biziosalonms.enums.StatusEnum;
import com.thebizio.biziosalonms.repo.CouponRepo;
import com.thebizio.biziosalonms.service.CouponService;
import com.thebizio.biziosalonms.utils.BaseControllerTestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CouponControllerTest extends BaseControllerTestCase {

    Coupon c1, c2, c3, c4;

    @Autowired
    private CouponService couponService;

    @Autowired
    private CouponRepo couponRepo;


    @BeforeEach
    public void beforeEach() {
        super.beforeEach();

        c1 = demoEntitiesGenerator.getCoupon();
        c2 = demoEntitiesGenerator.getCoupon(StatusEnum.ENABLED);
        c3 = demoEntitiesGenerator.getCoupon(StatusEnum.DISABLED);

    }

    @Test
    void getAllTestCoupon() throws Exception {

        c4 = demoEntitiesGenerator.getCoupon();
        mvc.perform(mvcReqHelper.setUp(get("/api/v1/coupons"), demoEntitiesGenerator.getAdminUser()))
                .andExpect(jsonPath("$[0].name", is(c4.getName())))
                .andExpect(jsonPath("$[0].value", is(Double.valueOf(c4.getValue()))))
                .andExpect(jsonPath("$[0].type", is(c4.getType().toString())))
                .andExpect(jsonPath("$[0].status", is(c4.getStatus().toString())))
                .andExpect(jsonPath("$[0].maxRedemptions", is(c4.getMaxRedemptions())));

        mvc.perform(mvcReqHelper.setUp(get("/api/v1/coupons?status=" + c2.getStatus()), demoEntitiesGenerator.getAdminUser()))
                .andExpect(jsonPath("$[0].name", is(c2.getName())))
                .andExpect(jsonPath("$[0].value", is(Double.valueOf(c2.getValue()))))
                .andExpect(jsonPath("$[0].type", is(c2.getType().toString())))
                .andExpect(jsonPath("$[0].status", is(c2.getStatus().toString())))
                .andExpect(jsonPath("$[0].maxRedemptions", is(c2.getMaxRedemptions())));

        mvc.perform(mvcReqHelper.setUp(get("/api/v1/coupons?name=" + c2.getName()), demoEntitiesGenerator.getAdminUser()))
                .andExpect(jsonPath("$[0].name", is(c2.getName())))
                .andExpect(jsonPath("$[0].value", is(Double.valueOf(c2.getValue()))))
                .andExpect(jsonPath("$[0].type", is(c2.getType().toString())))
                .andExpect(jsonPath("$[0].status", is(c2.getStatus().toString())))
                .andExpect(jsonPath("$[0].maxRedemptions", is(c2.getMaxRedemptions())));


        mvc.perform(mvcReqHelper.setUp(get("/api/v1/coupons?startDate=" + c2.getStartDate()), demoEntitiesGenerator.getAdminUser()))
                .andExpect(jsonPath("$[0].name", is(c2.getName())))
                .andExpect(jsonPath("$[0].value", is(Double.valueOf(c2.getValue()))))
                .andExpect(jsonPath("$[0].type", is(c2.getType().toString())))
                .andExpect(jsonPath("$[0].status", is(c2.getStatus().toString())))
                .andExpect(jsonPath("$[0].maxRedemptions", is(c2.getMaxRedemptions())));

        mvc.perform(mvcReqHelper.setUp(get("/api/v1/coupons?endDate=" + c2.getEndDate()), demoEntitiesGenerator.getAdminUser()))
                .andExpect(jsonPath("$[0].name", is(c2.getName())))
                .andExpect(jsonPath("$[0].value", is(Double.valueOf(c2.getValue()))))
                .andExpect(jsonPath("$[0].type", is(c2.getType().toString())))
                .andExpect(jsonPath("$[0].status", is(c2.getStatus().toString())))
                .andExpect(jsonPath("$[0].maxRedemptions", is(c2.getMaxRedemptions())));

    }


    @Test
    void getTest() throws Exception {

        mvc.perform(mvcReqHelper.setUp(get("/api/v1/coupons/" + c1.getId()), demoEntitiesGenerator.getAdminUser()))
                .andExpect(jsonPath("$.name", is(c1.getName())))
                .andExpect(jsonPath("$.value", is(Double.valueOf(c1.getValue()))))
                .andExpect(jsonPath("$.type", is(c1.getType().toString())))
                .andExpect(jsonPath("$.status", is(c1.getStatus().toString())))
                .andExpect(jsonPath("$.maxRedemptions", is(c1.getMaxRedemptions())));

    }

    @Test
    void createCouponTest() throws Exception {

        CouponCreateDto dto = new CouponCreateDto();
        dto.setName("SL_MNGT");
        dto.setValue(10);
        dto.setCouponType(CouponTypeEnum.PERCENT);
        dto.setStartDate(LocalDateTime.now());
        dto.setEndDate(LocalDateTime.now().plusDays(1));
        dto.setMaxRedemptions(5);

        mvc.perform(mvcReqHelper.setUp(post("/api/v1/coupons"), dto , demoEntitiesGenerator.getAdminUser()))
                .andExpect(jsonPath("$.name", is(dto.getName())))
                .andExpect(jsonPath("$.value", is(Double.valueOf(dto.getValue()))))
                .andExpect(jsonPath("$.type", is(dto.getCouponType().toString())))
                .andExpect(jsonPath("$.maxRedemptions", is(dto.getMaxRedemptions())))
                .andExpect(status().isOk());

        assertTrue(couponRepo.existsByName(dto.getName()));

        Coupon c4 = demoEntitiesGenerator.getCoupon();

        dto.setName(c4.getName());

        mvc.perform(mvcReqHelper.setUp(post("/api/v1/coupons"), dto , demoEntitiesGenerator.getAdminUser()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("coupon name already exists")));
    }

    @Test
    void updateCouponTest() throws Exception {

        CouponUpdateDto dto = new CouponUpdateDto();
        dto.setName("SALON_UP");

        mvc.perform(mvcReqHelper.setUp(put("/api/v1/coupons/" + c1.getId()), dto , demoEntitiesGenerator.getAdminUser()))
                .andExpect(status().isOk());

        c1 = couponService.getCouponById(c1.getId());
        assertEquals(c1.getName(), dto.getName());

        Coupon c4 = demoEntitiesGenerator.getCoupon();

        dto.setName(c4.getName());

        mvc.perform(mvcReqHelper.setUp(put("/api/v1/coupons/" + c1.getId()), dto , demoEntitiesGenerator.getAdminUser()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("coupon name already exists")));
    }

    @Test
    void statusTest() throws Exception {

        assertEquals(c2.getStatus(), StatusEnum.ENABLED);

        mvc.perform(mvcReqHelper.setUp(post("/api/v1/coupons/disable/" + c2.getId()), demoEntitiesGenerator.getAdminUser()))
                .andExpect(jsonPath("$.message", is("OK")));

        c2 = couponService.getCouponById(c2.getId());

        assertEquals(c2.getStatus(), StatusEnum.DISABLED);

        mvc.perform(mvcReqHelper.setUp(post("/api/v1/coupons/enable/" + c3.getId()), demoEntitiesGenerator.getAdminUser()))
                .andExpect(jsonPath("$.message", is("OK")));

        c3 = couponService.getCouponById(c3.getId());

        assertEquals(c3.getStatus(), StatusEnum.ENABLED);

        mvc.perform(mvcReqHelper.setUp(post("/api/v1/coupons/enable/" + c1.getId()), demoEntitiesGenerator.getAdminUser()))
                .andExpect(jsonPath("$.message", is("Coupon is already " + StatusEnum.ENABLED.toString().toLowerCase())));
    }

}

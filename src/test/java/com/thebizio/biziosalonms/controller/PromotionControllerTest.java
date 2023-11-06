package com.thebizio.biziosalonms.controller;

import com.thebizio.biziosalonms.dto.coupon.CouponCreateDto;
import com.thebizio.biziosalonms.dto.promotion.PromotionCreateDto;
import com.thebizio.biziosalonms.entity.Coupon;
import com.thebizio.biziosalonms.entity.Promotion;
import com.thebizio.biziosalonms.enums.CouponTypeEnum;
import com.thebizio.biziosalonms.enums.StatusEnum;
import com.thebizio.biziosalonms.repo.PromotionRepo;
import com.thebizio.biziosalonms.service.PromotionService;
import com.thebizio.biziosalonms.utils.BaseControllerTestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PromotionControllerTest extends BaseControllerTestCase {

    Promotion p1, p2, p3, p4;
    Coupon c1, c2;

    @Autowired
    private PromotionService promotionService;

    @Autowired
    private PromotionRepo promotionRepo;

    @BeforeEach
    public void beforeEach() {
        super.beforeEach();

        c1 = demoEntitiesGenerator.getCoupon();
        c2 = demoEntitiesGenerator.getCoupon(StatusEnum.DISABLED);

        p1 = demoEntitiesGenerator.getPromotion();
        p2 = demoEntitiesGenerator.getPromotion(StatusEnum.ENABLED);
        p3 = demoEntitiesGenerator.getPromotion(StatusEnum.DISABLED);

    }

    @Test
    void createPromoTest() throws Exception {

        PromotionCreateDto dto = new PromotionCreateDto();
        dto.setCode("GET-20");
        dto.setMaxRedemptions(1);
        dto.setCouponId(c1.getId());
        dto.setEndDate(LocalDateTime.now());

        mvc.perform(mvcReqHelper.setUp(post("/api/v1/promotions"), dto , demoEntitiesGenerator.getAdminUser()))
                .andExpect(jsonPath("$.code", is(dto.getCode())))
                .andExpect(jsonPath("$.maxRedemptions", is(dto.getMaxRedemptions())))
                .andExpect(status().isOk());

        assertTrue(promotionRepo.existsByCode(dto.getCode()));

        Promotion p4 = demoEntitiesGenerator.getPromotion();

        dto.setCode(p4.getCode());

        mvc.perform(mvcReqHelper.setUp(post("/api/v1/promotions"), dto , demoEntitiesGenerator.getAdminUser()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("promotion code already exists")));
    }

    @Test
    void getAllTestPromo() throws Exception {

        p4 = demoEntitiesGenerator.getPromotion();

        mvc.perform(mvcReqHelper.setUp(get("/api/v1/promotions"), demoEntitiesGenerator.getAdminUser()))
                .andExpect(jsonPath("$[0].code", is(p4.getCode())))
                .andExpect(jsonPath("$[0].timesRedeemed", is(p4.getTimesRedeemed())))
                .andExpect(jsonPath("$[0].status", is(p4.getStatus().toString())))
                .andExpect(jsonPath("$[0].maxRedemptions", is(p4.getMaxRedemptions())));

        mvc.perform(mvcReqHelper.setUp(get("/api/v1/promotions?status=" + p4.getStatus()), demoEntitiesGenerator.getAdminUser()))
                .andExpect(jsonPath("$[0].code", is(p4.getCode())))
                .andExpect(jsonPath("$[0].timesRedeemed", is(p4.getTimesRedeemed())))
                .andExpect(jsonPath("$[0].status", is(p4.getStatus().toString())))
                .andExpect(jsonPath("$[0].maxRedemptions", is(p4.getMaxRedemptions())));

        mvc.perform(mvcReqHelper.setUp(get("/api/v1/promotions?code=" + p4.getCode()), demoEntitiesGenerator.getAdminUser()))
                .andExpect(jsonPath("$[0].code", is(p4.getCode())))
                .andExpect(jsonPath("$[0].timesRedeemed", is(p4.getTimesRedeemed())))
                .andExpect(jsonPath("$[0].status", is(p4.getStatus().toString())))
                .andExpect(jsonPath("$[0].maxRedemptions", is(p4.getMaxRedemptions())));

        mvc.perform(mvcReqHelper.setUp(get("/api/v1/promotions?endDate=" + p4.getEndDate()), demoEntitiesGenerator.getAdminUser()))
                .andExpect(jsonPath("$[0].code", is(p4.getCode())))
                .andExpect(jsonPath("$[0].timesRedeemed", is(p4.getTimesRedeemed())))
                .andExpect(jsonPath("$[0].status", is(p4.getStatus().toString())))
                .andExpect(jsonPath("$[0].maxRedemptions", is(p4.getMaxRedemptions())));

        mvc.perform(mvcReqHelper.setUp(get("/api/v1/promotions?status="+p4.getStatus()+"&code="+p4.getCode()+
                        "&couponId="+p4.getCoupon().getId()+"&endDate=" + p4.getEndDate().toString()), demoEntitiesGenerator.getAdminUser()))
                .andExpect(jsonPath("$[0].code", is(p4.getCode())))
                .andExpect(jsonPath("$[0].timesRedeemed", is(p4.getTimesRedeemed())))
                .andExpect(jsonPath("$[0].status", is(p4.getStatus().toString())))
                .andExpect(jsonPath("$[0].maxRedemptions", is(p4.getMaxRedemptions())));

    }


    @Test
    void getPromoTest() throws Exception {

        mvc.perform(mvcReqHelper.setUp(get("/api/v1/promotions/" + p1.getId()), demoEntitiesGenerator.getAdminUser()))
                .andExpect(jsonPath("$.code", is(p1.getCode())))
                .andExpect(jsonPath("$.timesRedeemed", is(p1.getTimesRedeemed())))
                .andExpect(jsonPath("$.status", is(p1.getStatus().toString())))
                .andExpect(jsonPath("$.maxRedemptions", is(p1.getMaxRedemptions())));

    }

    @Test
    void statusTest() throws Exception {

        assertEquals(p2.getStatus(), StatusEnum.ENABLED);

        mvc.perform(mvcReqHelper.setUp(post("/api/v1/promotions/disable/" + p2.getId()), demoEntitiesGenerator.getAdminUser()))
                .andExpect(jsonPath("$.message", is("OK")));

        p2 = promotionService.findPromotionById(p2.getId());
        assertEquals(p2.getStatus(), StatusEnum.DISABLED);

        mvc.perform(mvcReqHelper.setUp(post("/api/v1/promotions/enable/" + p3.getId()), demoEntitiesGenerator.getAdminUser()))
                .andExpect(jsonPath("$.message", is("OK")));

        p3 = promotionService.findPromotionById(p3.getId());

        assertEquals(p3.getStatus(), StatusEnum.ENABLED);
        p1 = demoEntitiesGenerator.getPromotion(c2, StatusEnum.ENABLED);

        mvc.perform(mvcReqHelper.setUp(post("/api/v1/promotions/enable/" + p1.getId()), demoEntitiesGenerator.getAdminUser()))
                .andExpect(jsonPath("$.message", is("coupon is " + StatusEnum.DISABLED.toString().toLowerCase())));
    }

}

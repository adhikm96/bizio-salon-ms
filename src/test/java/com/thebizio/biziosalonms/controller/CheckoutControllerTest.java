package com.thebizio.biziosalonms.controller;

import com.thebizio.biziosalonms.dto.checkout.CheckoutPaymentDto;
import com.thebizio.biziosalonms.dto.checkout.CheckoutSessionDto;
import com.thebizio.biziosalonms.entity.*;
import com.thebizio.biziosalonms.enums.*;
import com.thebizio.biziosalonms.repo.AppointmentRepo;
import com.thebizio.biziosalonms.repo.InvoiceRepo;
import com.thebizio.biziosalonms.repo.PaymentRepo;
import com.thebizio.biziosalonms.repo.TaxScheduleRepo;
import com.thebizio.biziosalonms.utils.BaseControllerTestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class CheckoutControllerTest extends BaseControllerTestCase {

    @Autowired
    private TaxScheduleRepo taxScheduleRepo;

    @Autowired
    private PaymentRepo paymentRepo;

    @Autowired
    private InvoiceRepo invoiceRepo;

    @Autowired
    AppointmentRepo appointmentRepo;

    Appointment appointment1;
    Branch branch;
    @BeforeEach
    public void beforeEach() {
        super.beforeEach();
        branch = demoEntitiesGenerator.getBranch();
        appointment1 = demoEntitiesGenerator.getAppointment(AppointmentStatus.SCHEDULED,branch);
    }

    @Test
    void checkoutSessionWithoutDiscountAndTaxTest() throws Exception {
        Item item = demoEntitiesGenerator.getItem();
        CheckoutSessionDto dto = new CheckoutSessionDto();
        dto.setAppointmentId(appointment1.getId());
        dto.setItems(Collections.singletonList(item.getId()));

        mvc.perform(mvcReqHelper.setUp(post("/api/v1/checkout/session"),dto, demoEntitiesGenerator.getAdminUser()))
                .andExpect(jsonPath("$.netTotal", is(210.0)))
                .andExpect(jsonPath("$.items.length()", is(1)));
    }

    @Test
    @Transactional
    void checkoutSessionWithTaxTest() throws Exception {
        TaxSchedule taxSchedule = new TaxSchedule();
        List<TaxScheduleItem> taxScheduleItems = new ArrayList<>();
        taxScheduleItems.add(demoEntitiesGenerator.taxScheduleItem(branch,ChargeOnEnum.ITEM_PRICE,8,TaxChargeTypeEnum.PERCENT,0,0));
        taxScheduleItems.add(demoEntitiesGenerator.taxScheduleItem(branch,ChargeOnEnum.GROSS_TOTAL,10,TaxChargeTypeEnum.PERCENT,0,0));
        taxSchedule.setName("Tax Schedule");
        taxSchedule.setBranch(branch);
        taxSchedule.setStatus(StatusEnum.ENABLED);
        taxSchedule.setTaxScheduleItems(taxScheduleItems);
        taxScheduleRepo.saveAndFlush(taxSchedule);

        Item item = demoEntitiesGenerator.getItem();
        CheckoutSessionDto dto = new CheckoutSessionDto();
        dto.setAppointmentId(appointment1.getId());
        dto.setItems(Collections.singletonList(item.getId()));

        mvc.perform(mvcReqHelper.setUp(post("/api/v1/checkout/session"),dto, demoEntitiesGenerator.getAdminUser()))
                .andExpect(jsonPath("$.grossTotal", is(210.0)))
                .andExpect(jsonPath("$.discount", is(0.0)))
                .andExpect(jsonPath("$.taxes", is(37.8)))
                .andExpect(jsonPath("$.netTotal", is(247.8)))
                .andExpect(jsonPath("$.items.length()", is(1)));
    }


    @Test
    @Transactional
    void checkoutSessionWithDiscountAndTaxTest() throws Exception {
        Coupon coupon = demoEntitiesGenerator.getCoupon(CouponTypeEnum.PERCENT);
        Promotion promotion = demoEntitiesGenerator.getPromotion(coupon);

        TaxSchedule taxSchedule = new TaxSchedule();
        List<TaxScheduleItem> taxScheduleItems = new ArrayList<>();
        taxScheduleItems.add(demoEntitiesGenerator.taxScheduleItem(branch,ChargeOnEnum.ITEM_PRICE,8,TaxChargeTypeEnum.PERCENT,0,0));
        taxScheduleItems.add(demoEntitiesGenerator.taxScheduleItem(branch,ChargeOnEnum.GROSS_TOTAL,10,TaxChargeTypeEnum.PERCENT,0,0));
        taxSchedule.setName("Tax Schedule");
        taxSchedule.setBranch(branch);
        taxSchedule.setStatus(StatusEnum.ENABLED);
        taxSchedule.setTaxScheduleItems(taxScheduleItems);
        taxScheduleRepo.saveAndFlush(taxSchedule);

        Item item = demoEntitiesGenerator.getItem();
        CheckoutSessionDto dto = new CheckoutSessionDto();
        dto.setAppointmentId(appointment1.getId());
        dto.setItems(Collections.singletonList(item.getId()));
        dto.setPromoCode(promotion.getCode());

        mvc.perform(mvcReqHelper.setUp(post("/api/v1/checkout/session"),dto, demoEntitiesGenerator.getAdminUser()))
                .andExpect(jsonPath("$.grossTotal", is(210.0)))
                .andExpect(jsonPath("$.discount", is(31.5)))
                .andExpect(jsonPath("$.taxes", is(37.8)))
                .andExpect(jsonPath("$.netTotal", is(216.3)))
                .andExpect(jsonPath("$.items.length()", is(1)));
    }

    @Test
    @Transactional
    void checkoutInvoiceTest() throws Exception {
        Coupon coupon = demoEntitiesGenerator.getCoupon(CouponTypeEnum.PERCENT);
        Promotion promotion = demoEntitiesGenerator.getPromotion(coupon);

        TaxSchedule taxSchedule = new TaxSchedule();
        List<TaxScheduleItem> taxScheduleItems = new ArrayList<>();
        taxScheduleItems.add(demoEntitiesGenerator.taxScheduleItem(branch,ChargeOnEnum.ITEM_PRICE,8,TaxChargeTypeEnum.PERCENT,0,0));
        taxScheduleItems.add(demoEntitiesGenerator.taxScheduleItem(branch,ChargeOnEnum.GROSS_TOTAL,10,TaxChargeTypeEnum.PERCENT,0,0));
        taxSchedule.setName("Tax Schedule");
        taxSchedule.setBranch(branch);
        taxSchedule.setStatus(StatusEnum.ENABLED);
        taxSchedule.setTaxScheduleItems(taxScheduleItems);
        taxScheduleRepo.saveAndFlush(taxSchedule);

        Item item = demoEntitiesGenerator.getItem();
        CheckoutSessionDto dto = new CheckoutSessionDto();
        dto.setAppointmentId(appointment1.getId());
        dto.setItems(Collections.singletonList(item.getId()));
        dto.setPromoCode(promotion.getCode());

        assertEquals(0,invoiceRepo.findAll().size());

        mvc.perform(mvcReqHelper.setUp(post("/api/v1/checkout"),dto, demoEntitiesGenerator.getAdminUser()))
                .andExpect(jsonPath("$.postingDate", is(LocalDate.now().toString())))
                .andExpect(jsonPath("$.status", is(InvoiceStatus.UNPAID.toString())))
                .andExpect(jsonPath("$.items.length()", is(1)));

        assertEquals(1,invoiceRepo.findAll().size());
    }


    @Test
    @Transactional
    void checkoutPaymentTest() throws Exception {
        Invoice invoice = new Invoice();
        invoice.setStatus(InvoiceStatus.UNPAID);
        invoice.setNetTotal(200.0);
        invoiceRepo.save(invoice);

        appointment1.setInvoice(invoice);
        appointmentRepo.save(appointment1);

        CheckoutPaymentDto checkoutPaymentDto = new CheckoutPaymentDto();
        checkoutPaymentDto.setInvoiceId(invoice.getId());
        checkoutPaymentDto.setPaymentType(PaymentTypeEnum.CASH);

        assertEquals(0,paymentRepo.findAll().size());

        mvc.perform(mvcReqHelper.setUp(post("/api/v1/checkout/payment"),checkoutPaymentDto, demoEntitiesGenerator.getAdminUser()))
                .andExpect(jsonPath("$.paymentDate", is(LocalDate.now().toString())))
                .andExpect(jsonPath("$.paymentType", is(PaymentTypeEnum.CASH.toString())))
                .andExpect(jsonPath("$.invoice.status", is(InvoiceStatus.PAID.toString())));

        assertEquals(1,paymentRepo.findAll().size());
        assertEquals(AppointmentStatus.COMPLETED,appointmentRepo.findById(appointment1.getId()).get().getStatus());
    }

}

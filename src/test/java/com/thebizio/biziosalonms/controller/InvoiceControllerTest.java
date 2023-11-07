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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class InvoiceControllerTest extends BaseControllerTestCase {

    @Autowired
    private InvoiceRepo invoiceRepo;

    @Autowired
    AppointmentRepo appointmentRepo;

    Appointment appointment1,appointment2;
    Branch branch;

    @BeforeEach
    public void beforeEach() {
        super.beforeEach();
        branch = demoEntitiesGenerator.getBranch();
        appointment1 = demoEntitiesGenerator.getAppointment(AppointmentStatus.SCHEDULED,branch);
        appointment2 = demoEntitiesGenerator.getAppointment(AppointmentStatus.SCHEDULED,branch);
    }

    @Test
    @Transactional
    void listAllTest() throws Exception {
        Invoice invoice = new Invoice();
        invoice.setStatus(InvoiceStatus.UNPAID);
        invoice.setPostingDate(LocalDate.now());
        invoice.setNetTotal(200.0);
        invoice.setBranch(branch);
        invoice.setCustomerUser(appointment1.getCustomerUser());
        invoice.setAppointments(Collections.singletonList(appointment1));
        invoiceRepo.save(invoice);

        Invoice invoice2 = new Invoice();
        invoice2.setStatus(InvoiceStatus.PAID);
        invoice2.setNetTotal(300.0);
        invoice2.setBranch(branch);
        invoice2.setPostingDate(LocalDate.now().minusDays(1));
        invoice2.setCustomerUser(appointment2.getCustomerUser());
        invoice2.setAppointments(Collections.singletonList(appointment2));
        invoiceRepo.save(invoice2);

        mvc.perform(mvcReqHelper.setUp(get("/api/v1/invoices?status="+InvoiceStatus.PAID), demoEntitiesGenerator.getAdminUser()))
                .andExpect(jsonPath("$.length()", is(1)));

        mvc.perform(mvcReqHelper.setUp(get("/api/v1/invoices?customer="+invoice.getCustomerUser().getId().toString()), demoEntitiesGenerator.getAdminUser()))
                .andExpect(jsonPath("$.length()", is(1)));

        mvc.perform(mvcReqHelper.setUp(get("/api/v1/invoices?branch="+branch.getId().toString()), demoEntitiesGenerator.getAdminUser()))
                .andExpect(jsonPath("$.length()", is(2)));

        mvc.perform(mvcReqHelper.setUp(get("/api/v1/invoices"), demoEntitiesGenerator.getAdminUser()))
                .andExpect(jsonPath("$.length()", is(2)));

        mvc.perform(mvcReqHelper.setUp(get("/api/v1/invoices?postingDate="+invoice.getPostingDate().toString()), demoEntitiesGenerator.getAdminUser()))
                .andExpect(jsonPath("$.length()", is(1)));
    }

    @Test
    @Transactional
    void getInvoiceById() throws Exception {
        Invoice invoice = new Invoice();
        invoice.setStatus(InvoiceStatus.UNPAID);
        invoice.setNetTotal(200.0);
        invoice.setBranch(branch);
        invoice.setCustomerUser(appointment1.getCustomerUser());
        invoice.setAppointments(Collections.singletonList(appointment1));
        invoiceRepo.save(invoice);


        mvc.perform(mvcReqHelper.setUp(get("/api/v1/invoices/"+invoice.getId()), demoEntitiesGenerator.getAdminUser()))
                .andExpect(jsonPath("$.id", is(invoice.getId().toString())))
                .andExpect(jsonPath("$.status", is(InvoiceStatus.UNPAID.toString())));
    }

    @Test
    void payInvoice() throws Exception {
        Invoice invoice = new Invoice();
        invoice.setStatus(InvoiceStatus.UNPAID);
        invoice.setNetTotal(200.0);
        invoice.setBranch(branch);
        invoice.setCustomerUser(appointment1.getCustomerUser());
        invoiceRepo.save(invoice);

        mvc.perform(mvcReqHelper.setUp(post("/api/v1/invoices/"+invoice.getId()+"/pay"), demoEntitiesGenerator.getAdminUser()))
                .andExpect(jsonPath("$.message", is("OK")));

        assertEquals(InvoiceStatus.PAID,invoiceRepo.findById(invoice.getId()).get().getStatus());
    }

}

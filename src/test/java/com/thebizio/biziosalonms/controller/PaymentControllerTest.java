package com.thebizio.biziosalonms.controller;

import com.thebizio.biziosalonms.entity.Branch;
import com.thebizio.biziosalonms.entity.Invoice;
import com.thebizio.biziosalonms.entity.Payment;
import com.thebizio.biziosalonms.utils.BaseControllerTestCase;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class PaymentControllerTest extends BaseControllerTestCase {
    @Test
    void testList() throws Exception {

        Payment payment = demoEntitiesGenerator.getPayment();

        mvc.perform(mvcReqHelper.setUp(get("/api/v1/payments"), demoEntitiesGenerator.getAdminUser()))
                .andExpect(jsonPath("$.length()", is(1)))
                .andExpect(jsonPath("$[0].paymentType", is(payment.getPaymentType().toString().toString())))
                .andExpect(jsonPath("$[0].paymentDate", is(payment.getPaymentDate().toString().toString())))
                .andExpect(jsonPath("$[0].paymentRef", is(payment.getPaymentRef())))
                .andExpect(jsonPath("$[0].invoiceId", is(payment.getInvoice().getId().toString())))
                .andDo(print());

        Branch branch = demoEntitiesGenerator.getBranch();

        payment = demoEntitiesGenerator.getPayment(branch);

        mvc.perform(mvcReqHelper.setUp(get("/api/v1/payments?branch=" + branch.getId()), demoEntitiesGenerator.getAdminUser()))
                .andExpect(jsonPath("$.length()", is(1)))
                .andExpect(jsonPath("$[0].paymentType", is(payment.getPaymentType().toString())))
                .andExpect(jsonPath("$[0].paymentDate", is(payment.getPaymentDate().toString())))
                .andExpect(jsonPath("$[0].paymentRef", is(payment.getPaymentRef())))
                .andExpect(jsonPath("$[0].invoiceId", is(payment.getInvoice().getId().toString())))
                .andDo(print());

        LocalDate now = LocalDate.now().minusDays(1);
        payment = demoEntitiesGenerator.getPayment(now);

        mvc.perform(mvcReqHelper.setUp(get("/api/v1/payments?paymentDate=" + now), demoEntitiesGenerator.getAdminUser()))
                .andExpect(jsonPath("$.length()", is(1)))
                .andExpect(jsonPath("$[0].paymentType", is(payment.getPaymentType().toString())))
                .andExpect(jsonPath("$[0].paymentDate", is(payment.getPaymentDate().toString())))
                .andExpect(jsonPath("$[0].paymentRef", is(payment.getPaymentRef())))
                .andExpect(jsonPath("$[0].invoiceId", is(payment.getInvoice().getId().toString())))
                .andDo(print());

        Invoice invoice = demoEntitiesGenerator.getInvoice();
        payment = demoEntitiesGenerator.getPayment(invoice);

        mvc.perform(mvcReqHelper.setUp(get("/api/v1/payments?invoice=" + invoice.getId().toString()), demoEntitiesGenerator.getAdminUser()))
                .andExpect(jsonPath("$.length()", is(1)))
                .andExpect(jsonPath("$[0].paymentType", is(payment.getPaymentType().toString())))
                .andExpect(jsonPath("$[0].paymentDate", is(payment.getPaymentDate().toString())))
                .andExpect(jsonPath("$[0].paymentRef", is(payment.getPaymentRef())))
                .andExpect(jsonPath("$[0].invoiceId", is(payment.getInvoice().getId().toString())))
                .andDo(print());
    }
}

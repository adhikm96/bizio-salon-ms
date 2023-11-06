package com.thebizio.biziosalonms.service;

import com.thebizio.biziosalonms.entity.Invoice;
import com.thebizio.biziosalonms.entity.Payment;
import com.thebizio.biziosalonms.enums.PaymentTypeEnum;
import com.thebizio.biziosalonms.repo.PaymentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepo paymentRepo;

    public Payment createPayment(PaymentTypeEnum paymentType, String paymentRef, Invoice invoice){
        Payment payment = new Payment();
        if (paymentType.equals(PaymentTypeEnum.CASH)) payment.setPaymentDate(LocalDate.now());
        payment.setPaymentType(paymentType);
        payment.setPaymentRef(paymentRef);
        payment.setInvoice(invoice);
        return paymentRepo.save(payment);
    }

}

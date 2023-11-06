package com.thebizio.biziosalonms.utils;

import com.thebizio.biziosalonms.repo.*;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataCleaner {

    @Autowired
    SalonUserRepo salonUserRepo;

    @Autowired
    BranchRepo branchRepo;

    @Autowired
    WorkScheduleRepo workScheduleRepo;

    @Autowired
    CustomerRepo customerRepo;

    @Autowired
    AppointmentRepo appointmentRepo;

    @Autowired
    ItemRepo itemRepo;

    @Autowired
    CompanyRepo companyRepo;

    @Autowired
    TaxScheduleRepo taxScheduleRepo;

    @Autowired
    TaxHeadRepo taxHeadRepo;

    @Autowired
    PromotionRepo promotionRepo;

    @Autowired
    CouponRepo couponRepo;

    @Autowired
    PaymentRepo paymentRepo;

    @Autowired
    InvoiceRepo invoiceRepo;

    public void clean() {
        promotionRepo.deleteAll();
        couponRepo.deleteAll();
        paymentRepo.deleteAll();
        invoiceRepo.deleteAll();
        taxScheduleRepo.deleteAll();
        appointmentRepo.deleteAll();
        paymentRepo.deleteAll();
        invoiceRepo.deleteAll();
        taxScheduleRepo.deleteAll();
        itemRepo.deleteAll();
        taxHeadRepo.deleteAll();
        customerRepo.deleteAll();
        salonUserRepo.deleteAll();
        branchRepo.deleteAll();
        workScheduleRepo.deleteAll();
        companyRepo.deleteAll();
    }
}

package com.thebizio.biziosalonms.utils;

import com.thebizio.biziosalonms.repo.*;
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
    InvoiceRepo invoiceRepo;

    @Autowired
    PaymentRepo paymentRepo;

    public void clean() {
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
        promotionRepo.deleteAll();
        companyRepo.deleteAll();
    }
}

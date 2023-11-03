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

    public void clean() {
        taxScheduleRepo.deleteAll();
        appointmentRepo.deleteAll();
        itemRepo.deleteAll();
        taxHeadRepo.deleteAll();
        customerRepo.deleteAll();
        salonUserRepo.deleteAll();
        branchRepo.deleteAll();
        workScheduleRepo.deleteAll();
        companyRepo.deleteAll();
    }
}

package com.thebizio.biziosalonms.utils;

import com.thebizio.biziosalonms.repo.BranchRepo;
import com.thebizio.biziosalonms.repo.SalonUserRepo;
import com.thebizio.biziosalonms.repo.WorkScheduleRepo;
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

    public void clean() {
        salonUserRepo.deleteAll();
        branchRepo.deleteAll();
        workScheduleRepo.deleteAll();
    }
}

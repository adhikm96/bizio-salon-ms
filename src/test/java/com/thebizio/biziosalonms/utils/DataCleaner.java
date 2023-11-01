package com.thebizio.biziosalonms.utils;

import com.thebizio.biziosalonms.repo.SalonUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataCleaner {

    @Autowired
    SalonUserRepo salonUserRepo;

    public void clean() {
        salonUserRepo.deleteAll();
    }
}

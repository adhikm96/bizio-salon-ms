package com.thebizio.biziosalonms.utils;

import com.thebizio.biziosalonms.entity.*;
import com.thebizio.biziosalonms.enums.BranchStatusEnum;
import com.thebizio.biziosalonms.enums.EmpType;
import com.thebizio.biziosalonms.enums.PaySchedule;
import com.thebizio.biziosalonms.enums.StatusEnum;
import com.thebizio.biziosalonms.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

@Service
public class DemoEntitiesGenerator {
    Random random = new Random();

    @Autowired
    AddressRepo addressRepo;

    @Autowired
    SalonUserRepo salonUserRepo;

    @Autowired
    BranchRepo branchRepo;

    @Autowired
    CompanyRepo companyRepo;

    @Autowired
    WorkScheduleRepo workScheduleRepo;

    public WorkSchedule getWorkSchedule() {
        WorkSchedule workSchedule = new WorkSchedule();
        workSchedule.setName("9 to 5");
        workSchedule.setStatus(StatusEnum.ENABLED);
        return workScheduleRepo.save(workSchedule) ;
    }


    public Company getCompany() {
        Company company = new Company();
        company.setEmail(fakeEmail());
        company.setCity("Mumbai");
        company.setCountry("INDIA");
        company.setState("MH");
        company.setZipcode("123456");
        company.setName("Company-A");
        return companyRepo.save(company);
    }

    public Branch getBranch() {
        Branch branch = new Branch();
        branch.setEmail(fakeEmail());
        branch.setCity("Mumbai");
        branch.setCountry("INDIA");
        branch.setState("MH");
        branch.setStatus(BranchStatusEnum.OPENED);
        branch.setZipcode("123456");
        branch.setName("Branch-A");
        branch.setCompany(getCompany());
        return branchRepo.save(branch);
    }

    public Address getAddress() {
        Address address = new Address();
        address.setStreetAddress1("Address Line1");
        address.setStreetAddress2("Address Line1");
        address.setCity("St.Albert");
        address.setZipcode("T8N");
        address.setState("Alberta");
        address.setCountry("Canada");
        return addressRepo.save(address);
    }

    public String fakeEmail() {
        int __ = random.nextInt(1000);
        return "email" + __ + "@example" + __ + ".com";
    }

    String fakeUsername() {
        int __ = random.nextInt(1000);
        return "user" + __;
    }

    public User getSalonUser() {
        User user = new User();
        user.setAddress(getAddress());

        user.setEmail(fakeEmail());
        user.setUsername(fakeUsername());
        user.setEmpCode(UUID.randomUUID().toString());
        user.setEmpType(EmpType.CONTRACT);
        user.setStatus(StatusEnum.ENABLED);
        user.setPaySchedule(PaySchedule.MONTHLY);
        return salonUserRepo.save(user);
    }

    public User getAdminUser() {
        User user = new User();
        user.setAddress(getAddress());

        user.setEmail(fakeEmail());
        user.setUsername(fakeUsername());
        user.setEmpCode(UUID.randomUUID().toString());
        user.setEmpType(EmpType.CONTRACT);
        user.setStatus(StatusEnum.ENABLED);
        user.setPaySchedule(PaySchedule.MONTHLY);

        return user;
    }

    public User getSalonUser(WorkSchedule workSchedule) {
        User user = new User();
        user.setAddress(getAddress());

        user.setEmail(fakeEmail());
        user.setUsername(fakeUsername());
        user.setEmpCode(UUID.randomUUID().toString());
        user.setEmpType(EmpType.CONTRACT);
        user.setStatus(StatusEnum.ENABLED);
        user.setPaySchedule(PaySchedule.MONTHLY);
        user.setWorkSchedule(workSchedule);
        return salonUserRepo.save(user);
    }

    public User getSalonUser(Branch branch) {
        User user = new User();
        user.setAddress(getAddress());

        user.setEmail(fakeEmail());
        user.setUsername(fakeUsername());
        user.setEmpCode(UUID.randomUUID().toString());
        user.setEmpType(EmpType.CONTRACT);
        user.setStatus(StatusEnum.ENABLED);
        user.setPaySchedule(PaySchedule.MONTHLY);
        user.setBranch(branch);
        return salonUserRepo.save(user);
    }

    public User getSalonUser(Branch branch, WorkSchedule workSchedule) {
        User user = new User();
        user.setAddress(getAddress());

        user.setEmail(fakeEmail());
        user.setUsername(fakeUsername());
        user.setEmpCode(UUID.randomUUID().toString());
        user.setEmpType(EmpType.CONTRACT);
        user.setStatus(StatusEnum.ENABLED);
        user.setPaySchedule(PaySchedule.MONTHLY);
        user.setBranch(branch);
        user.setWorkSchedule(workSchedule);
        return salonUserRepo.save(user);
    }

    public User getSalonUser(Branch branch, WorkSchedule workSchedule, StatusEnum statusEnum) {
        User user = new User();
        user.setAddress(getAddress());

        user.setEmail(fakeEmail());
        user.setUsername(fakeUsername());
        user.setEmpCode(UUID.randomUUID().toString());
        user.setEmpType(EmpType.CONTRACT);
        user.setStatus(statusEnum);
        user.setPaySchedule(PaySchedule.MONTHLY);
        user.setBranch(branch);
        user.setWorkSchedule(workSchedule);
        return salonUserRepo.save(user);
    }

    public User getSalonUser(StatusEnum statusEnum) {
        User user = new User();
        user.setAddress(getAddress());

        user.setEmail(fakeEmail());
        user.setUsername(fakeUsername());
        user.setEmpCode(UUID.randomUUID().toString());
        user.setEmpType(EmpType.CONTRACT);

        user.setStatus(statusEnum);
        user.setPaySchedule(PaySchedule.MONTHLY);
        return salonUserRepo.save(user);
    }

    public User getSalonUser(StatusEnum statusEnum, EmpType empType) {
        User user = new User();
        user.setAddress(getAddress());

        user.setEmail(fakeEmail());
        user.setUsername(fakeUsername());
        user.setEmpCode(UUID.randomUUID().toString());
        user.setEmpType(empType);

        user.setStatus(statusEnum);
        user.setPaySchedule(PaySchedule.MONTHLY);
        return salonUserRepo.save(user);
    }

    public User getSalonUser(StatusEnum statusEnum, PaySchedule paySchedule) {
        User user = new User();
        user.setAddress(getAddress());

        user.setEmail(fakeEmail());
        user.setUsername(fakeUsername());
        user.setEmpCode(UUID.randomUUID().toString());
        user.setEmpType(EmpType.CONTRACT);

        user.setStatus(statusEnum);
        user.setPaySchedule(paySchedule);
        return salonUserRepo.save(user);
    }
}

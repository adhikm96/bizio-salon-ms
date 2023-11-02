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
        company.setAddress(getAddress());
        company.setStatus(StatusEnum.ENABLED);
        company.setName("Company-A");
        company.setContactNo(UUID.randomUUID().toString());
        return companyRepo.save(company);
    }

    public Company getCompany(StatusEnum statusEnum) {
        Company company = new Company();
        company.setEmail(fakeEmail());
        company.setAddress(getAddress());
        company.setStatus(statusEnum);
        company.setName("Company-A");
        company.setContactNo(UUID.randomUUID().toString());
        return companyRepo.save(company);
    }

    public Company getCompany(StatusEnum statusEnum, String name) {
        Company company = new Company();
        company.setEmail(fakeEmail());
        company.setAddress(getAddress());
        company.setStatus(statusEnum);
        company.setName(name);
        company.setContactNo(UUID.randomUUID().toString());
        return companyRepo.save(company);
    }

    public Company getCompany(StatusEnum statusEnum, String name, String zip) {
        Company company = new Company();
        company.setEmail(fakeEmail());
        company.setAddress(getAddress());
        company.setStatus(statusEnum);
        company.setName(name);
        company.setContactNo(UUID.randomUUID().toString());

        company.getAddress().setZipcode(zip);
        addressRepo.save(company.getAddress());

        return companyRepo.save(company);
    }


    public Company getCompany(String name) {
        Company company = new Company();
        company.setEmail(fakeEmail());
        company.setAddress(getAddress());
        company.setStatus(StatusEnum.ENABLED);
        company.setName(name);
        company.setContactNo(UUID.randomUUID().toString());
        return companyRepo.save(company);
    }

    public Company getCompanyWithZip( String zip) {
        Company company = new Company();
        company.setEmail(fakeEmail());
        company.setAddress(getAddress());
        company.getAddress().setZipcode(zip);

        addressRepo.save(company.getAddress());
        company.setStatus(StatusEnum.ENABLED);
        company.setName("Company-A");
        company.setContactNo(UUID.randomUUID().toString());
        return companyRepo.save(company);
    }

    public Company getCompanyWithZip( String zip, String name) {
        Company company = new Company();
        company.setEmail(fakeEmail());
        company.setAddress(getAddress());
        company.getAddress().setZipcode(zip);

        addressRepo.save(company.getAddress());
        company.setStatus(StatusEnum.ENABLED);
        company.setName(name);
        company.setContactNo(UUID.randomUUID().toString());
        return companyRepo.save(company);
    }

    public Company getCompanyWithZip( String zip, StatusEnum statusEnum) {
        Company company = new Company();
        company.setEmail(fakeEmail());
        company.setAddress(getAddress());
        company.getAddress().setZipcode(zip);

        addressRepo.save(company.getAddress());
        company.setStatus(statusEnum);
        company.setName("Company-A");
        company.setContactNo(UUID.randomUUID().toString());
        return companyRepo.save(company);
    }

    public Branch getBranch() {
        Branch branch = new Branch();
        branch.setEmail(fakeEmail());
        branch.setAddress(getAddress());
        branch.setStatus(BranchStatusEnum.OPENED);
        branch.setName("Branch-A");
        branch.setCompany(getCompany());
        branch.setWorkSchedule(getWorkSchedule());
        return branchRepo.save(branch);
    }

    public Branch getBranch(BranchStatusEnum statusEnum) {
        Branch branch = new Branch();
        branch.setEmail(fakeEmail());
        branch.setAddress(getAddress());
        branch.setStatus(statusEnum);
        branch.setName("Branch-A");
        branch.setCompany(getCompany());
        branch.setWorkSchedule(getWorkSchedule());
        return branchRepo.save(branch);
    }

    public Branch getBranchWithZip(String zipcode) {
        Branch branch = new Branch();
        branch.setEmail(fakeEmail());
        branch.setAddress(getAddress());

        branch.getAddress().setZipcode(zipcode);

        addressRepo.save(branch.getAddress());

        branch.setStatus(BranchStatusEnum.OPENED);
        branch.setName("Branch-A");
        branch.setCompany(getCompany());
        branch.setWorkSchedule(getWorkSchedule());
        return branchRepo.save(branch);
    }

    public Branch getBranchWithEmail(String email) {
        Branch branch = new Branch();
        branch.setEmail(email);
        branch.setAddress(getAddress());
        branch.setStatus(BranchStatusEnum.OPENED);
        branch.setName("Branch-A");
        branch.setCompany(getCompany());
        branch.setWorkSchedule(getWorkSchedule());
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

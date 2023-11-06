package com.thebizio.biziosalonms.utils;

import com.thebizio.biziosalonms.entity.*;
import com.thebizio.biziosalonms.enums.*;
import com.thebizio.biziosalonms.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.UUID;

@Service
public class DemoEntitiesGenerator {

    String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    Random random = new Random();

    @Autowired
    AddressRepo addressRepo;

    @Autowired
    PaymentRepo paymentRepo;

    @Autowired
    SalonUserRepo salonUserRepo;

    @Autowired
    BranchRepo branchRepo;

    @Autowired
    CompanyRepo companyRepo;

    @Autowired
    WorkScheduleRepo workScheduleRepo;

    @Autowired
    AppointmentRepo appointmentRepo;

    @Autowired
    CustomerRepo customerRepo;

    @Autowired
    ItemRepo itemRepo;

    @Autowired
    TaxHeadRepo taxHeadRepo;

    @Autowired
    private CouponRepo couponRepo;


    public String getUniqueCode() {
        StringBuilder sb = new StringBuilder(5);
        for (int i = 0; i < 5; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }

    public Appointment getAppointment(AppointmentStatus status){
        Appointment appointment = new Appointment();
        appointment.setCustomerUser(getCustomer());
        appointment.setAppointmentDate(LocalDate.now());
        appointment.setAppointmentTime(LocalTime.now());
        appointment.setBranch(getBranch());
        appointment.setStatus(status);
        appointment.setAssignedTo(getSalonUser());
        return appointmentRepo.save(appointment);
    }

    public CustomerUser getCustomer(){
        CustomerUser customer = new CustomerUser();
        customer.setFirstName("Fist Name "+getUniqueCode());
        customer.setLastName("Last Name "+getUniqueCode());
        customer.setEmail(fakeEmail());
        customer.setUsername("Username"+getUniqueCode());
        return customerRepo.save(customer);
    }

    public Item getItem(){
        Item item = new Item();
        item.setName("Item "+getUniqueCode());
        item.setCode("IC"+getUniqueCode());
        item.setStatus(StatusEnum.ENABLED);
        item.setCost(200.0);
        item.setPrice(210.0);
        item.setItemType(ItemType.SERVICE);
        return itemRepo.save(item);
    }

    public TaxHead getTaxHead(){
        TaxHead taxHead = new TaxHead();
        taxHead.setName("Tax "+getUniqueCode());
        taxHead.setCode("TXCODE"+getUniqueCode());
        taxHead.setStatus(StatusEnum.ENABLED);
        taxHead.setChargeOn(ChargeOnEnum.GROSS_TOTAL);
        return taxHeadRepo.save(taxHead);
    }



    public WorkSchedule getWorkSchedule() {
        WorkSchedule workSchedule = new WorkSchedule();
        workSchedule.setName("9 to 5");
        workSchedule.setStatus(StatusEnum.ENABLED);

        workSchedule.appendItem(getWorkScheduleItem());
        return workScheduleRepo.save(workSchedule) ;
    }

    public WorkSchedule getWorkSchedule(StatusEnum statusEnum) {
        WorkSchedule workSchedule = new WorkSchedule();
        workSchedule.setName("9 to 5");
        workSchedule.setStatus(statusEnum);
        return workScheduleRepo.save(workSchedule) ;
    }

    public WorkSchedule getWorkSchedule(Branch branch) {
        WorkSchedule workSchedule = new WorkSchedule();
        workSchedule.setName("9 to 5");
        workSchedule.setStatus(StatusEnum.ENABLED);
        workScheduleRepo.save(workSchedule);
        branch.setWorkSchedule(workSchedule);
        branchRepo.save(branch);
        return workSchedule;
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

    public WorkScheduleItem getWorkScheduleItem() {
        WorkScheduleItem item = new WorkScheduleItem();

        item.setDay(WorkScheduleDayEnum.MONDAY);
        item.setEndTime(LocalTime.now().plusHours(3).truncatedTo(ChronoUnit.SECONDS));
        item.setStartTime(LocalTime.now().minusHours(3).truncatedTo(ChronoUnit.SECONDS));

        item.setBreakStartTime(LocalTime.now().plusHours(1).truncatedTo(ChronoUnit.SECONDS));
        item.setBreakEndTime(LocalTime.now().plusHours(2).truncatedTo(ChronoUnit.SECONDS));

        return item;
    }

    public Payment getPayment() {
        Payment payment = new Payment();
        payment.setPaymentDate(LocalDate.now());
        payment.setPaymentRef("PAYMENT_" + UUID.randomUUID().toString().substring(0,4));
        payment.setPaymentType(PaymentTypeEnum.CASH);
        payment.setInvoice(getInvoice());

        return paymentRepo.save(payment);
    }

    public Payment getPayment(Branch branch) {
        Payment payment = new Payment();
        payment.setPaymentDate(LocalDate.now());
        payment.setPaymentRef("PAYMENT_" + UUID.randomUUID().toString().substring(0,4));
        payment.setPaymentType(PaymentTypeEnum.CASH);
        payment.setInvoice(getInvoice(branch));

        return paymentRepo.save(payment);
    }

    public Payment getPayment(Invoice invoice) {
        Payment payment = new Payment();
        payment.setPaymentDate(LocalDate.now());
        payment.setPaymentRef("PAYMENT_" + UUID.randomUUID().toString().substring(0,4));
        payment.setPaymentType(PaymentTypeEnum.CASH);
        payment.setInvoice(invoice);

        return paymentRepo.save(payment);
    }

    public Payment getPayment(LocalDate postingDate) {
        Payment payment = new Payment();
        payment.setPaymentDate(postingDate);
        payment.setPaymentRef("PAYMENT_" + UUID.randomUUID().toString().substring(0,4));
        payment.setPaymentType(PaymentTypeEnum.CASH);
        payment.setInvoice(getInvoice());

        return paymentRepo.save(payment);
    }

    @Autowired
    InvoiceRepo invoiceRepo;

    public Invoice getInvoice() {
        Invoice invoice = new Invoice();
        invoice.setBranch(getBranch());

        // set other fields

        return invoiceRepo.save(invoice);
    }

    public Invoice getInvoice(Branch branch) {
        Invoice invoice = new Invoice();
        invoice.setBranch(branch);

        // set other fields

        return invoiceRepo.save(invoice);
    }

    public Coupon getCoupon(){

        Coupon c1 = new Coupon();
        c1.setName("SALON");
        c1.setValue(10);
        c1.setMaxRedemptions(50);
        c1.setType(CouponTypeEnum.AMOUNT);
        c1.setStatus(StatusEnum.ENABLED);
        c1.setStartDate(LocalDateTime.now());
        c1.setEndDate(LocalDateTime.now());

        return couponRepo.save(c1);

    }

    public Coupon getCoupon(StatusEnum status){

        Coupon c1 = new Coupon();
        c1.setName("SALON");
        c1.setValue(10);
        c1.setMaxRedemptions(50);
        c1.setType(CouponTypeEnum.AMOUNT);
        c1.setStatus(status);
        c1.setStartDate(LocalDateTime.now());
        c1.setEndDate(LocalDateTime.now());

        return couponRepo.save(c1);
    }

}

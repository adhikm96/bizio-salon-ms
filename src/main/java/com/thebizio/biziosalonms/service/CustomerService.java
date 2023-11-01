package com.thebizio.biziosalonms.service;

import com.thebizio.biziosalonms.dto.Customer.CreateUpdateCustomerDto;
import com.thebizio.biziosalonms.dto.Customer.CustomerDetailDto;
import com.thebizio.biziosalonms.entity.CustomerUser;
import com.thebizio.biziosalonms.enums.StatusEnum;
import com.thebizio.biziosalonms.exception.AlreadyExistsException;
import com.thebizio.biziosalonms.exception.NotFoundException;
import com.thebizio.biziosalonms.exception.ValidationException;
import com.thebizio.biziosalonms.projection.customer.CustomerDetailPrj;
import com.thebizio.biziosalonms.projection.customer.CustomerListPrj;
import com.thebizio.biziosalonms.repo.CustomerRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private ModelMapper modelMapper;


    public List<CustomerListPrj> getAllUser(Optional<String> email, Optional<String> mobile, Optional<StatusEnum> status, Optional<String> zipCode) {
        if (email.isPresent()) return customerRepo.findByEmail(email.get());
        if (mobile.isPresent()) return customerRepo.findByMobile(mobile.get());
        if (status.isPresent() && zipCode.isPresent()) return customerRepo.findAllByStatusAndZipcode(status.get(),zipCode.get());
        if (status.isPresent()) return customerRepo.findAllByStatus(status.get());
        if (zipCode.isPresent()) return customerRepo.findAllByZipcode(zipCode.get());
        else return customerRepo.findAllCustomer();
    }

    public CustomerDetailPrj getUserById(UUID userId) {
        return customerRepo.findCustomerById(userId).orElseThrow(() -> new NotFoundException("Customer not found"));
    }

    private CustomerUser fetchCustomerById(UUID userId) {
        return customerRepo.findById(userId).orElseThrow(() -> new NotFoundException("Customer not found"));
    }

    public String toggleUser(UUID userId, StatusEnum status) {
        CustomerUser cu = fetchCustomerById(userId);
        if (cu.getStatus().equals(status)) throw new ValidationException("Customer is already "+status.toString().toLowerCase());
        cu.setStatus(status);
        customerRepo.save(cu);
        return ConstantMsg.OK;
    }

    public CustomerDetailDto createCustomer(CreateUpdateCustomerDto dto) {
        if (customerRepo.existsByEmail(dto.getEmail())) throw new AlreadyExistsException("Email already exists");
        if (dto.getUsername() != null && !dto.getUsername().isEmpty() && customerRepo.existsByUsername(dto.getUsername())) throw new AlreadyExistsException("Username already exists");
        CustomerUser cu = new CustomerUser();
        return modelMapper.map(setCustomerDetails(cu,dto), CustomerDetailDto.class);
    }

    public CustomerDetailDto updateCustomer(UUID userId, CreateUpdateCustomerDto dto) {
        CustomerUser cu = fetchCustomerById(userId);
        if (!cu.getEmail().equals(dto.getEmail()) && customerRepo.existsByEmail(dto.getEmail())) throw new AlreadyExistsException("Email already exists");
        if (dto.getUsername() != null && !dto.getUsername().isEmpty() && !cu.getUsername().equals(dto.getUsername()) && customerRepo.existsByUsername(dto.getUsername())) throw new AlreadyExistsException("Username already exists");
        return modelMapper.map(setCustomerDetails(cu,dto), CustomerDetailDto.class);
    }

     CustomerUser setCustomerDetails(CustomerUser cu,CreateUpdateCustomerDto dto){
        cu.setFirstName(dto.getFirstName());
        cu.setLastName(dto.getLastName());
        cu.setUsername(dto.getUsername());
        cu.setEmail(dto.getEmail());
        cu.setMobile(dto.getMobile());
        cu.setGender(dto.getGender());
        cu.setStreetAddress1(dto.getStreetAddress1());
        cu.setStreetAddress2(dto.getStreetAddress2());
        cu.setCity(dto.getCity());
        cu.setState(dto.getState());
        cu.setCountry(dto.getCountry());
        cu.setZipcode(dto.getZipcode());
        cu.setFederation(dto.getFederation());
        cu.setStatus(StatusEnum.ENABLED);
        return customerRepo.save(cu);
    }
}

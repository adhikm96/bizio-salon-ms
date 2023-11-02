package com.thebizio.biziosalonms.service;

import com.thebizio.biziosalonms.dto.address.AddressDto;
import com.thebizio.biziosalonms.dto.company.CompanyUpdateDto;
import com.thebizio.biziosalonms.entity.Address;
import com.thebizio.biziosalonms.entity.Company;
import com.thebizio.biziosalonms.enums.StatusEnum;
import com.thebizio.biziosalonms.exception.NotFoundException;
import com.thebizio.biziosalonms.exception.ValidationException;
import com.thebizio.biziosalonms.projection.company.CompanyDetailPrj;
import com.thebizio.biziosalonms.projection.company.CompanyListPrj;
import com.thebizio.biziosalonms.repo.AddressRepo;
import com.thebizio.biziosalonms.repo.CompanyRepo;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CompanyService {

    final CompanyRepo companyRepo;

    final AddressRepo addressRepo;

    final ModelMapper modelMapper;


    final AddressService addressService;

    public CompanyService(CompanyRepo companyRepo, AddressRepo addressRepo, ModelMapper modelMapper, AddressService addressService) {
        this.companyRepo = companyRepo;
        this.addressRepo = addressRepo;
        this.modelMapper = modelMapper;
        this.addressService = addressService;
    }

    public List<CompanyListPrj> list(
            @RequestParam Optional<StatusEnum> status,
            @RequestParam Optional<String> name,
            @RequestParam Optional<String> zipcode
    ) {
        if(status.isPresent() && name.isPresent() && zipcode.isPresent())
            return companyRepo.fetchAllByZipCodeStatusName(zipcode.get(), status.get(), name.get());
        if(status.isPresent() && name.isPresent())
            return companyRepo.fetchAllByStatusName(status.get(), name.get());
        if(status.isPresent() && zipcode.isPresent())
            return companyRepo.fetchAllByZipCodeStatus(zipcode.get(), status.get());
        if(name.isPresent() && zipcode.isPresent())
            return companyRepo.fetchAllByZipCodeName(zipcode.get(), name.get());
        if(name.isPresent())
            return companyRepo.fetchAllByName(name.get());
        if(zipcode.isPresent())
            return companyRepo.fetchAllByZipCode(zipcode.get());
        if(status.isPresent())
            return companyRepo.fetchAllByStatus(status.get());
        return companyRepo.fetchAll();
    }

    public Company findById(UUID id) {
        return companyRepo.findById(id).orElseThrow(() -> new NotFoundException("company not found"));
    }

    public CompanyDetailPrj fetchById(UUID id) {
        return companyRepo.fetchById(id).orElseThrow(() -> new NotFoundException("company not found"));
    }

    @Transactional
    public void update(UUID companyId, CompanyUpdateDto dto) {
        Company company = findById(companyId);

        // setting name
        company.setName(dto.getName());

        if(!company.getEmail().equals(dto.getEmail())) checkUniqueEmail(dto.getEmail());

        // setting email
        company.setEmail(dto.getEmail());

        if(company.getAddress() == null) company.setAddress(new Address());

        // setting address
        addressService.setAddressFields(company.getAddress(), modelMapper.map(dto, AddressDto.class));

        addressRepo.save(company.getAddress());

        company.setContactNo(dto.getContactNo());
        companyRepo.save(company);
    }

    private void checkUniqueEmail(String email) {
        if(companyRepo.existsByEmail(email)) {
            throw new ValidationException("email already exists");
        }
    }
}

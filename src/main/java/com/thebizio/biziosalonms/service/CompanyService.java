package com.thebizio.biziosalonms.service;

import com.thebizio.biziosalonms.dto.company.CompanyUpdateDto;
import com.thebizio.biziosalonms.entity.Company;
import com.thebizio.biziosalonms.entity.User;
import com.thebizio.biziosalonms.exception.NotFoundException;
import com.thebizio.biziosalonms.exception.ValidationException;
import com.thebizio.biziosalonms.projection.company.CompanyDetailPrj;
import com.thebizio.biziosalonms.projection.company.CompanyListPrj;
import com.thebizio.biziosalonms.repo.CompanyRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CompanyService {

    final CompanyRepo companyRepo;

    public CompanyService(CompanyRepo companyRepo) {
        this.companyRepo = companyRepo;
    }

    public List<CompanyListPrj> list() {
        return companyRepo.fetchAll();
    }

    public Company findById(UUID id) {
        return companyRepo.findById(id).orElseThrow(() -> new NotFoundException("company not found"));
    }

    public CompanyDetailPrj fetchById(UUID id) {
        return companyRepo.fetchById(id).orElseThrow(() -> new NotFoundException("company not found"));
    }

    public void update(UUID companyId, CompanyUpdateDto dto) {
        Company company = findById(companyId);

        company.setName(dto.getName());

        if(!company.getEmail().equals(dto.getEmail())) checkUniqueEmail(dto.getEmail());

        company.setEmail(dto.getEmail());

        company.setStreetAddress1(dto.getStreetAddress1());
        company.setStreetAddress2(dto.getStreetAddress2());
        company.setCity(dto.getCity());
        company.setState(dto.getState());
        company.setCountry(dto.getCountry());
        company.setZipcode(dto.getZipcode());
        company.setContactNo(dto.getContactNo());

        companyRepo.save(company);
    }

    private void checkUniqueEmail(String email) {
        if(companyRepo.existsByEmail(email)) {
            throw new ValidationException("email already exists");
        }
    }
}

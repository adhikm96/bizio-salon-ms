package com.thebizio.biziosalonms.controller;

import com.thebizio.biziosalonms.dto.company.CompanyUpdateDto;
import com.thebizio.biziosalonms.projection.company.CompanyDetailPrj;
import com.thebizio.biziosalonms.projection.company.CompanyListPrj;
import com.thebizio.biziosalonms.service.CompanyService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/companies")
public class CompanyController {

    final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    List<CompanyListPrj> listCompanies(){
        return companyService.list();
    }

    @GetMapping("/{companyId}")
    CompanyDetailPrj fetchByCompanyId(@PathVariable UUID companyId) {
        return companyService.fetchById(companyId);
    }

    @PutMapping("/{companyId}")
    void updateCompany(@PathVariable UUID companyId, @RequestBody @Valid CompanyUpdateDto dto) {
        companyService.update(companyId, dto);
    }
}

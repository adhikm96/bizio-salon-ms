package com.thebizio.biziosalonms.service;

import com.thebizio.biziosalonms.dto.tax_head.CreateUpdateTaxHeadDto;
import com.thebizio.biziosalonms.dto.tax_head.TaxHeadDetailDto;
import com.thebizio.biziosalonms.entity.Item;
import com.thebizio.biziosalonms.entity.TaxHead;
import com.thebizio.biziosalonms.enums.StatusEnum;
import com.thebizio.biziosalonms.exception.AlreadyExistsException;
import com.thebizio.biziosalonms.exception.NotFoundException;
import com.thebizio.biziosalonms.exception.ValidationException;
import com.thebizio.biziosalonms.projection.tax_head.TaxHeadDetailPrj;
import com.thebizio.biziosalonms.repo.TaxHeadRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaxHeadService {

    @Autowired
    private TaxHeadRepo taxHeadRepo;

    @Autowired
    private ModelMapper modelMapper;

    public List<TaxHeadDetailPrj> getAllTax(Optional<String> name, Optional<String> code, Optional<StatusEnum> status) {
        if (code.isPresent()) return taxHeadRepo.findAllByCode(code.get());
        if (name.isPresent() && status.isPresent()) return taxHeadRepo.findAllByNameAndStatus(name.get(),status.get());
        if (name.isPresent()) return taxHeadRepo.findAllByName(name.get());
        if (status.isPresent()) return taxHeadRepo.findAllByStatus(status.get());
        else return taxHeadRepo.findAllTaxHead();

    }

    public TaxHead fetchById(UUID taxId) {
        return taxHeadRepo.findById(taxId).orElseThrow(() -> new NotFoundException("Tax head not found"));
    }

    public TaxHeadDetailPrj getTaxById(UUID taxId) {
        return taxHeadRepo.findTaxHeadById(taxId).orElseThrow(() -> new NotFoundException("Tax head not found"));
    }

    public TaxHeadDetailDto createTax(CreateUpdateTaxHeadDto dto) {
        TaxHead th = new TaxHead();
        if (taxHeadRepo.existsByCode(dto.getCode())) throw new AlreadyExistsException("Tax code already exists");
        th.setName(dto.getName());
        th.setCode(dto.getCode());
        th.setStatus(StatusEnum.ENABLED);
        th.setChargeOn(dto.getChargeOn());
        return modelMapper.map(taxHeadRepo.save(th),TaxHeadDetailDto.class);
    }

    public TaxHeadDetailDto updateTax(UUID taxId, CreateUpdateTaxHeadDto dto) {
        TaxHead th = fetchById(taxId);
        if (!th.getCode().equals(dto.getCode()) && taxHeadRepo.existsByCode(dto.getCode())) throw new AlreadyExistsException("Tax code already exists");
        th.setName(dto.getName());
        th.setCode(dto.getCode());
        th.setChargeOn(dto.getChargeOn());
        return modelMapper.map(taxHeadRepo.save(th),TaxHeadDetailDto.class);
    }

    public String toggleTax(UUID taxId, StatusEnum status) {
        TaxHead th = fetchById(taxId);
        if (th.getStatus().equals(status)) throw new ValidationException("Tax head is already "+status.toString().toLowerCase());
        th.setStatus(status);
        taxHeadRepo.save(th);
        return ConstantMsg.OK;
    }
}

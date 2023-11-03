package com.thebizio.biziosalonms.service;

import com.thebizio.biziosalonms.dto.tax_schedule.CreateUpdateTaxScheduleDto;
import com.thebizio.biziosalonms.dto.tax_schedule.CreateUpdateTaxScheduleItemDto;
import com.thebizio.biziosalonms.dto.tax_schedule.TaxScheduleLitDto;
import com.thebizio.biziosalonms.entity.Branch;
import com.thebizio.biziosalonms.entity.TaxHead;
import com.thebizio.biziosalonms.entity.TaxSchedule;
import com.thebizio.biziosalonms.entity.TaxScheduleItem;
import com.thebizio.biziosalonms.enums.StatusEnum;
import com.thebizio.biziosalonms.exception.NotFoundException;
import com.thebizio.biziosalonms.exception.ValidationException;
import com.thebizio.biziosalonms.repo.TaxScheduleRepo;
import com.thebizio.biziosalonms.specification.TaxScheduleSpecification;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaxScheduleService {

    @Autowired
    private TaxScheduleRepo taxScheduleRepo;

    @Autowired
    private BranchService branchService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TaxHeadService taxHeadService;

    public List<TaxScheduleLitDto> getAllTaxSchedule(Optional<String> name, Optional<UUID> branch, Optional<StatusEnum> status) {
        List<TaxSchedule> taxSchedules = taxScheduleRepo.findAll(TaxScheduleSpecification.findWithFilter(name, branch, status),
                Sort.by(Sort.Direction.DESC,"modified"));

        return modelMapper.map(taxSchedules, new TypeToken<List<TaxScheduleLitDto>>(){}.getType());
    }

    public TaxSchedule fetchById(UUID taxScheduleId){
        return taxScheduleRepo.findById(taxScheduleId).orElseThrow(() -> new NotFoundException("Tax schedule not found"));
    }
    public TaxScheduleLitDto getTaxScheduleById(UUID taxScheduleId) {
        return modelMapper.map(fetchById(taxScheduleId),TaxScheduleLitDto.class);
    }


    public TaxScheduleLitDto createTaxSchedule(CreateUpdateTaxScheduleDto dto) {
        return modelMapper.map(setTaxScheduleDetail(new TaxSchedule(),dto),TaxScheduleLitDto.class);
    }

    public TaxScheduleLitDto updateTaxSchedule(UUID taxScheduleId,CreateUpdateTaxScheduleDto dto) {
        TaxSchedule ts = fetchById(taxScheduleId);
        return modelMapper.map(setTaxScheduleDetail(ts,dto),TaxScheduleLitDto.class);
    }

    private TaxSchedule setTaxScheduleDetail(TaxSchedule ts,CreateUpdateTaxScheduleDto dto){
        Branch branch = branchService.findById(dto.getBranchId());
        ts.setName(dto.getName());
        if (ts.getStatus() == null) ts.setStatus(StatusEnum.ENABLED);
        ts.setBranch(branch);

        List<TaxScheduleItem> taxScheduleItemList = new ArrayList<>();
        for (CreateUpdateTaxScheduleItemDto tsiDto : dto.getTaxScheduleItems()){
            TaxScheduleItem tsi = new TaxScheduleItem();
            tsi.setTaxHead(taxHeadService.fetchById(tsiDto.getTaxHeadId()));
            tsi.setBranch(branch);
            tsi.setTaxChargeType(tsiDto.getTaxChargeType());
            tsi.setValue(tsiDto.getValue());
            tsi.setOnValueFrom(tsiDto.getOnValueFrom());
            tsi.setOnValueTo(tsiDto.getOnValueTo());
            taxScheduleItemList.add(tsi);
        }
        return taxScheduleRepo.save(ts);
    }

    public String toggleTaxSchedule(UUID taxScheduleId, StatusEnum status) {
        TaxSchedule ts = fetchById(taxScheduleId);
        if (ts.getStatus().equals(status)) throw new ValidationException("Tax head is already "+status.toString().toLowerCase());
        ts.setStatus(status);
        taxScheduleRepo.save(ts);
        return ConstantMsg.OK;
    }
}

package com.thebizio.biziosalonms.service;

import com.thebizio.biziosalonms.dto.checkout.GrossTotalTaxDetailDto;
import com.thebizio.biziosalonms.dto.tax_schedule.CreateUpdateTaxScheduleDto;
import com.thebizio.biziosalonms.dto.tax_schedule.TaxScheduleDetailDto;
import com.thebizio.biziosalonms.dto.tax_schedule.TaxScheduleListDto;
import com.thebizio.biziosalonms.dto.tax_schedule_item.CreateUpdateTaxScheduleItemDto;
import com.thebizio.biziosalonms.dto.tax_schedule_item.ItemTaxDetailDto;
import com.thebizio.biziosalonms.entity.Branch;
import com.thebizio.biziosalonms.entity.Item;
import com.thebizio.biziosalonms.entity.TaxSchedule;
import com.thebizio.biziosalonms.entity.TaxScheduleItem;
import com.thebizio.biziosalonms.enums.ChargeOnEnum;
import com.thebizio.biziosalonms.enums.StatusEnum;
import com.thebizio.biziosalonms.enums.TaxChargeTypeEnum;
import com.thebizio.biziosalonms.exception.NotFoundException;
import com.thebizio.biziosalonms.exception.ValidationException;
import com.thebizio.biziosalonms.repo.TaxScheduleRepo;
import com.thebizio.biziosalonms.specification.TaxScheduleSpecification;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

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

    @Autowired
    private CalculateUtilService calculateUtilService;

    public List<TaxScheduleListDto> getAllTaxSchedule(Optional<String> name, Optional<UUID> branch, Optional<StatusEnum> status) {
        List<TaxSchedule> taxSchedules = taxScheduleRepo.findAll(TaxScheduleSpecification.findWithFilter(name, branch, status),
                Sort.by(Sort.Direction.DESC,"modified"));

        return modelMapper.map(taxSchedules, new TypeToken<List<TaxScheduleListDto>>(){}.getType());
    }

    public TaxSchedule fetchById(UUID taxScheduleId){
        return taxScheduleRepo.findById(taxScheduleId).orElseThrow(() -> new NotFoundException("Tax schedule not found"));
    }
    public TaxScheduleDetailDto getTaxScheduleById(UUID taxScheduleId) {
        return modelMapper.map(fetchById(taxScheduleId), TaxScheduleDetailDto.class);
    }


    public TaxScheduleDetailDto createTaxSchedule(CreateUpdateTaxScheduleDto dto) {
        return modelMapper.map(setTaxScheduleDetail(new TaxSchedule(),dto), TaxScheduleDetailDto.class);
    }

    public TaxScheduleDetailDto updateTaxSchedule(UUID taxScheduleId, CreateUpdateTaxScheduleDto dto) {
        TaxSchedule ts = fetchById(taxScheduleId);
        return modelMapper.map(setTaxScheduleDetail(ts,dto), TaxScheduleDetailDto.class);
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

        ts.setTaxScheduleItems(taxScheduleItemList);
        return taxScheduleRepo.save(ts);
    }

    public String toggleTaxSchedule(UUID taxScheduleId, StatusEnum status) {
        TaxSchedule ts = fetchById(taxScheduleId);
        if (ts.getStatus().equals(status)) throw new ValidationException("Tax head is already "+status.toString().toLowerCase());
        ts.setStatus(status);
        taxScheduleRepo.save(ts);
        return ConstantMsg.OK;
    }

    public TaxSchedule getTaxScheduleOfBranch(Branch branch){
        return taxScheduleRepo.findByBranchAndStatus(branch,StatusEnum.ENABLED).orElse(null);
    }


    public Set<ItemTaxDetailDto> calculateTaxOfItem(TaxSchedule taxSchedule, Item item,List<TaxScheduleItem> taxScheduleItems){
        if (taxScheduleItems.isEmpty()) return Collections.singleton(setItemTaxDetail(item, null));

        Set<ItemTaxDetailDto> itemTaxDetailSet = new HashSet<>();
        taxScheduleItems.forEach(taxItem -> {
            if ((taxItem.getOnValueFrom() == 0 || taxItem.getOnValueFrom() < item.getPrice()) && (taxItem.getOnValueTo() == 0 || taxItem.getOnValueTo() >= item.getPrice())
                    && taxItem.getTaxHead().getChargeOn().equals(ChargeOnEnum.ITEM_PRICE)){
                itemTaxDetailSet.add(setItemTaxDetail(item,taxItem));
            }
        });
        return itemTaxDetailSet;
    }

    private ItemTaxDetailDto setItemTaxDetail(Item item,TaxScheduleItem taxItem){
        ItemTaxDetailDto itemTaxDetail = new ItemTaxDetailDto();
        itemTaxDetail.setItemName(item.getName());
        itemTaxDetail.setItemCode(item.getCode());
        itemTaxDetail.setPrice(item.getPrice());
        if (taxItem != null){
            itemTaxDetail.setTaxName(taxItem.getTaxHead().getName());
            itemTaxDetail.setTaxCode(taxItem.getTaxHead().getCode());
            itemTaxDetail.setTaxChargeType(taxItem.getTaxChargeType());
            itemTaxDetail.setTax((double) taxItem.getValue());
            itemTaxDetail.setTaxCalculated(taxItem.getTaxChargeType().equals(TaxChargeTypeEnum.AMOUNT) ? (double) taxItem.getValue():calculateUtilService.roundTwoDigits(item.getPrice() * taxItem.getValue()/100));
        }
        return itemTaxDetail;
    }

    public Set<GrossTotalTaxDetailDto> calculateTaxOfGrossTotal(TaxSchedule taxSchedule, Double grossTotal,List<TaxScheduleItem> taxScheduleItems){
        Set<GrossTotalTaxDetailDto> grossTotalTaxDetailSet = new HashSet<>();
        if (taxScheduleItems.isEmpty()) return grossTotalTaxDetailSet;

        taxScheduleItems.forEach(taxItem -> {
            if ((taxItem.getOnValueFrom() == 0 || taxItem.getOnValueFrom() < grossTotal) && (taxItem.getOnValueTo() == 0 || taxItem.getOnValueTo() >= grossTotal)
                    && taxItem.getTaxHead().getChargeOn().equals(ChargeOnEnum.GROSS_TOTAL)){
                GrossTotalTaxDetailDto grossTotalTaxDetail = new GrossTotalTaxDetailDto();
                grossTotalTaxDetail.setTaxName(taxItem.getTaxHead().getName());
                grossTotalTaxDetail.setTaxCode(taxItem.getTaxHead().getCode());
                grossTotalTaxDetail.setGrossTotal(grossTotal);
                grossTotalTaxDetail.setTaxChargeType(taxItem.getTaxChargeType());
                grossTotalTaxDetail.setTax((double) taxItem.getValue());
                grossTotalTaxDetail.setTaxCalculated(taxItem.getTaxChargeType().equals(TaxChargeTypeEnum.AMOUNT) ? (double) taxItem.getValue():calculateUtilService.roundTwoDigits(grossTotal * taxItem.getValue()/100));
                grossTotalTaxDetailSet.add(grossTotalTaxDetail);
            }
        });
        return grossTotalTaxDetailSet;
    }
}

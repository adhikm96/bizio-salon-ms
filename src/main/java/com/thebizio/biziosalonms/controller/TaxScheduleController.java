package com.thebizio.biziosalonms.controller;

import com.thebizio.biziosalonms.dto.ResponseMessageDto;
import com.thebizio.biziosalonms.dto.tax_head.CreateUpdateTaxHeadDto;
import com.thebizio.biziosalonms.dto.tax_head.TaxHeadDetailDto;
import com.thebizio.biziosalonms.dto.tax_schedule.CreateUpdateTaxScheduleDto;
import com.thebizio.biziosalonms.dto.tax_schedule.TaxScheduleLitDto;
import com.thebizio.biziosalonms.enums.StatusEnum;
import com.thebizio.biziosalonms.projection.tax_head.TaxHeadDetailPrj;
import com.thebizio.biziosalonms.service.TaxScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tax-schedules")
public class TaxScheduleController {

    @Autowired
    private TaxScheduleService taxScheduleService;

    @GetMapping
    ResponseEntity<List<TaxScheduleLitDto>> getAll(@RequestParam Optional<String> name, @RequestParam Optional<UUID> branch,
                                                   @RequestParam Optional<StatusEnum> status) {
        return ResponseEntity.ok(taxScheduleService.getAllTaxSchedule(name,branch,status));
    }

    @GetMapping("/{taxScheduleId}")
    ResponseEntity<TaxScheduleLitDto> getById(@PathVariable UUID taxScheduleId) {
        return ResponseEntity.ok(taxScheduleService.getTaxScheduleById(taxScheduleId));
    }

    @PostMapping
    public ResponseEntity<TaxScheduleLitDto> create(@RequestBody @Valid CreateUpdateTaxScheduleDto dto){
        return ResponseEntity.ok(taxScheduleService.createTaxSchedule(dto));
    }

    @PutMapping("/{taxScheduleId}")
    public ResponseEntity<TaxScheduleLitDto> update(@PathVariable UUID taxScheduleId,@RequestBody @Valid CreateUpdateTaxScheduleDto dto){
        return ResponseEntity.ok(taxScheduleService.updateTaxSchedule(taxScheduleId,dto));
    }

    @PostMapping("/enable/{taxScheduleId}")
    public ResponseEntity<ResponseMessageDto> enableTaxSchedule(@PathVariable(name = "taxId") UUID taxScheduleId){
        return ResponseEntity.ok(new ResponseMessageDto(taxScheduleService.toggleTaxSchedule(taxScheduleId, StatusEnum.ENABLED)));
    }

    @PostMapping("/disable/{taxScheduleId}")
    public ResponseEntity<ResponseMessageDto> disableTaxSchedule(@PathVariable(name = "taxId") UUID taxScheduleId) {
        return ResponseEntity.ok(new ResponseMessageDto(taxScheduleService.toggleTaxSchedule(taxScheduleId, StatusEnum.DISABLED)));
    }

}

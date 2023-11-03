package com.thebizio.biziosalonms.controller;

import com.thebizio.biziosalonms.dto.ResponseMessageDto;
import com.thebizio.biziosalonms.dto.tax_head.CreateUpdateTaxHeadDto;
import com.thebizio.biziosalonms.dto.tax_head.TaxHeadDetailDto;
import com.thebizio.biziosalonms.enums.StatusEnum;
import com.thebizio.biziosalonms.projection.tax_head.TaxHeadDetailPrj;
import com.thebizio.biziosalonms.service.TaxHeadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tax-heads")
public class TaxHeadController {

    @Autowired
    private TaxHeadService taxHeadService;

    @GetMapping
    ResponseEntity<List<TaxHeadDetailPrj>> getAll(@RequestParam Optional<String> name, @RequestParam Optional<String> code,
                                                  @RequestParam Optional<StatusEnum> status) {
        return ResponseEntity.ok(taxHeadService.getAllTax(name,code,status));
    }

    @GetMapping("/{taxId}")
    ResponseEntity<TaxHeadDetailPrj> getById(@PathVariable UUID taxId) {
        return ResponseEntity.ok(taxHeadService.getTaxById(taxId));
    }

    @PostMapping
    public ResponseEntity<TaxHeadDetailDto> create(@RequestBody @Valid CreateUpdateTaxHeadDto dto){
        return ResponseEntity.ok(taxHeadService.createTax(dto));
    }

    @PutMapping("/{taxId}")
    public ResponseEntity<TaxHeadDetailDto> update(@PathVariable UUID taxId,@RequestBody @Valid CreateUpdateTaxHeadDto dto){
        return ResponseEntity.ok(taxHeadService.updateTax(taxId,dto));
    }

    @PostMapping("/enable/{taxId}")
    public ResponseEntity<ResponseMessageDto> enableTax(@PathVariable(name = "taxId") UUID taxId){
        return ResponseEntity.ok(new ResponseMessageDto(taxHeadService.toggleTax(taxId, StatusEnum.ENABLED)));
    }

    @PostMapping("/disable/{taxId}")
    public ResponseEntity<ResponseMessageDto> disableTax(@PathVariable(name = "taxId") UUID taxId) {
        return ResponseEntity.ok(new ResponseMessageDto(taxHeadService.toggleTax(taxId, StatusEnum.DISABLED)));
    }
}

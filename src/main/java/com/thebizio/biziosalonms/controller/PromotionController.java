package com.thebizio.biziosalonms.controller;

import com.thebizio.biziosalonms.dto.ResponseMessageDto;
import com.thebizio.biziosalonms.dto.promotion.PromotionCreateDto;
import com.thebizio.biziosalonms.dto.promotion.PromotionDetailDto;
import com.thebizio.biziosalonms.enums.StatusEnum;
import com.thebizio.biziosalonms.projection.promotion.PromotionDetailPrj;
import com.thebizio.biziosalonms.projection.promotion.PromotionListPrj;
import com.thebizio.biziosalonms.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/promotions")
public class PromotionController {

    @Autowired
    private PromotionService promotionService;

    @PostMapping
    ResponseEntity<PromotionDetailDto> createPromotion(@RequestBody @Valid PromotionCreateDto createDto) {
        return ResponseEntity.ok(promotionService.savePromotion(createDto));
    }

    @GetMapping
    ResponseEntity<List<PromotionListPrj>> findAll(@RequestParam Optional<StatusEnum> status,
                                                   @RequestParam Optional<UUID> couponId,
                                                   @RequestParam Optional<String> code,
                                                   @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Optional<LocalDateTime> endDate) {
        return ResponseEntity.ok(promotionService.getAllPromotion(status, couponId, code, endDate));
    }

    @GetMapping("/{promotionId}")
    ResponseEntity<PromotionDetailPrj> getById(@PathVariable UUID promotionId) {
        return ResponseEntity.ok(promotionService.getById(promotionId));
    }


    @PostMapping("/enable/{promotion_id}")
    public ResponseEntity<ResponseMessageDto> enablePromotion(@PathVariable(name = "promotion_id") UUID promotionId){
        return ResponseEntity.ok(new ResponseMessageDto(promotionService.togglePromotion(promotionId, StatusEnum.ENABLED)));
    }

    @PostMapping("/disable/{promotion_id}")
    public ResponseEntity<ResponseMessageDto> disablePromotion(@PathVariable(name = "promotion_id") UUID promotionId) {
        return ResponseEntity.ok(new ResponseMessageDto(promotionService.togglePromotion(promotionId, StatusEnum.DISABLED)));
    }

}

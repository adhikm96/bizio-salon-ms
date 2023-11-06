package com.thebizio.biziosalonms.controller;

import com.thebizio.biziosalonms.dto.ResponseMessageDto;
import com.thebizio.biziosalonms.dto.coupon.CouponCreateDto;
import com.thebizio.biziosalonms.dto.coupon.CouponDetailsDto;
import com.thebizio.biziosalonms.dto.coupon.CouponUpdateDto;
import com.thebizio.biziosalonms.enums.StatusEnum;
import com.thebizio.biziosalonms.projection.coupon.CouponDetailPrj;
import com.thebizio.biziosalonms.projection.coupon.CouponListPrj;
import com.thebizio.biziosalonms.service.CouponService;
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
@RequestMapping("/api/v1/coupons")
public class CouponController {

    @Autowired
    private CouponService couponService;

    @PostMapping
    ResponseEntity<CouponDetailsDto> createCoupon(@RequestBody @Valid CouponCreateDto createDto) {
        return ResponseEntity.ok(couponService.saveCoupon(createDto));
    }

    @GetMapping
    ResponseEntity<List<CouponListPrj>> findAll(@RequestParam Optional<StatusEnum> status,
                                                @RequestParam Optional<String> name,
                                                @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Optional<LocalDateTime> startDate,
                                                @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Optional<LocalDateTime> endDate) {
        return ResponseEntity.ok(couponService.getAll(status, name, startDate, endDate));
    }

    @GetMapping("/{couponId}")
    ResponseEntity<CouponDetailPrj> getById(@PathVariable UUID couponId) {
        return ResponseEntity.ok(couponService.getById(couponId));
    }

    @PutMapping("/{couponId}")
    void update(@PathVariable UUID couponId, @RequestBody @Valid CouponUpdateDto updateDto) {
        couponService.updateCoupon(couponId, updateDto);
    }

    @PostMapping("/enable/{coupon_id}")
    public ResponseEntity<ResponseMessageDto> enableCoupon(@PathVariable(name = "coupon_id") UUID coupon_id) {
        return ResponseEntity.ok(new ResponseMessageDto(couponService.toggleCoupon(coupon_id, StatusEnum.ENABLED)));
    }

    @PostMapping("/disable/{coupon_id}")
    public ResponseEntity<ResponseMessageDto> disableCoupon(@PathVariable(name = "coupon_id") UUID coupon_id) {
        return ResponseEntity.ok(new ResponseMessageDto(couponService.toggleCoupon(coupon_id, StatusEnum.DISABLED)));
    }

}

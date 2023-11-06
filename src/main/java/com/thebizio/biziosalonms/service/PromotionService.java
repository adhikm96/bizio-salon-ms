package com.thebizio.biziosalonms.service;

import com.thebizio.biziosalonms.entity.Coupon;
import com.thebizio.biziosalonms.entity.Promotion;
import com.thebizio.biziosalonms.exception.NotFoundException;
import com.thebizio.biziosalonms.repo.CouponRepo;
import com.thebizio.biziosalonms.repo.PromotionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PromotionService {

    @Autowired
    private PromotionRepo promotionRepo;

    @Autowired
    private CouponRepo couponRepo;

    @Autowired
    private CalculateUtilService calculatedUtilService;

    public Promotion findByCode(String code){
        return promotionRepo.findByCode(code).orElseThrow(() -> new NotFoundException("promotion not found"));
    }

    public void incrementPromoCodeCounter(Promotion promotion) {
        promotion.setTimesRedeemed(promotion.getTimesRedeemed() + 1);
        promotionRepo.save(promotion);
    }

    public void decrementPromoCodeCounter(Promotion promotion) {
        promotion.setTimesRedeemed(promotion.getTimesRedeemed() - 1);
        promotionRepo.save(promotion);
    }
}

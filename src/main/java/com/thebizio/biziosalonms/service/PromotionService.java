package com.thebizio.biziosalonms.service;

import com.thebizio.biziosalonms.dto.promotion.PromotionCreateDto;
import com.thebizio.biziosalonms.dto.promotion.PromotionDetailDto;
import com.thebizio.biziosalonms.entity.Coupon;
import com.thebizio.biziosalonms.entity.Promotion;
import com.thebizio.biziosalonms.enums.StatusEnum;
import com.thebizio.biziosalonms.exception.NotFoundException;
import com.thebizio.biziosalonms.exception.ValidationException;
import com.thebizio.biziosalonms.projection.promotion.PromotionDetailPrj;
import com.thebizio.biziosalonms.projection.promotion.PromotionListPrj;
import com.thebizio.biziosalonms.repo.CouponRepo;
import com.thebizio.biziosalonms.repo.PromotionRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PromotionService {

    @Autowired
    private PromotionRepo promotionRepo;

    @Autowired
    private CouponRepo couponRepo;

    @Autowired
    private CalculateUtilService calculatedUtilService;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private StrUtil strUtil;

    @Autowired
    private CouponService couponService;

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


    public List<PromotionListPrj> getAllPromotion(Optional<StatusEnum> status, Optional<UUID> couponId,
                                                  Optional<String> code, Optional<LocalDateTime> endDate) {
        if (status.isPresent() && code.isPresent() && couponId.isPresent() && endDate.isPresent())
            return promotionRepo.getAllByStatusAndCodeAndCouponIdAndEndDate(status.get(), code.get(), couponId.get(), endDate.get());
        if (status.isPresent() && code.isPresent())
            return promotionRepo.getAllByStatusAndCode(status.get(), code.get());
        if (status.isPresent() && couponId.isPresent())
            return promotionRepo.getAllByStatusAndCouponId(status.get(), couponId.get());
        if (status.isPresent() && endDate.isPresent())
            return promotionRepo.getAllByStatusAndEndDate(status.get(), endDate.get());
        if (status.isPresent()) return promotionRepo.getAllByStatus(status.get());
        if (code.isPresent()) return promotionRepo.getAllByCode(code.get());
        if (endDate.isPresent()) return promotionRepo.getAllByEndDate(endDate.get());
        if (couponId.isPresent()) return promotionRepo.getAllByCouponId(couponId.get());
        return promotionRepo.getAll();
    }

    public PromotionDetailPrj getById(UUID id){
        return promotionRepo.fetchById(id).orElseThrow(() -> new NotFoundException("promotion not found"));
    }

    public PromotionDetailDto savePromotion(PromotionCreateDto promoCreateDto){

        Coupon c = couponService.getCouponById(promoCreateDto.getCouponId());
        Integer sumTimesRedeemed = promotionRepo.sumByTimesRedeemedAndCoupon(c);

        if (sumTimesRedeemed != null && (sumTimesRedeemed + promoCreateDto.getMaxRedemptions() > c.getMaxRedemptions())){
            throw new ValidationException("Promotion's max redemptions can not exceed coupon's max redemption");
        }

        if(sumTimesRedeemed == null && promoCreateDto.getMaxRedemptions() > c.getMaxRedemptions()){
            throw new ValidationException("Promotion's max redemptions can not exceed coupon's max redemption");
        }

        if (promoCreateDto.getEndDate().isBefore(c.getStartDate()) || promoCreateDto.getEndDate().isAfter(c.getEndDate())){
            throw new ValidationException("Promotion end date must be between coupon start and end dates");
        }

        Promotion p = new Promotion();

        if(promoCreateDto.getCode() == null || promoCreateDto.getCode().equals("")) {
            p.setCode(generateUniquePromoCode());
        }
        else{
            checkUniqueCode(promoCreateDto);
            if (promoCreateDto.getCode().length() < 6){
                throw new ValidationException("Promocode must be at least 6 chars long");
            }
            p.setCode(promoCreateDto.getCode());
        }
        p.setEndDate(promoCreateDto.getEndDate());
        p.setStatus (StatusEnum.ENABLED);
        p.setCoupon(c);
        p.setTimesRedeemed(0);
        p.setMaxRedemptions(promoCreateDto.getMaxRedemptions());
        promotionRepo.save(p);

        return modelMapper.map(p, PromotionDetailDto.class);
    }


    private void checkUniqueCode(PromotionCreateDto createDto) {
        if(promotionRepo.existsByCode(createDto.getCode())) throw new ValidationException("promotion code already exists");
    }

    private String generateUniquePromoCode() {
        String code = strUtil.generateRandomPromocodeString();

        if (promotionRepo.existsByCode(code)) {
            // recursion call if the code already exists
            return generateUniquePromoCode();
        }
        return code;
    }

    public Promotion findPromotionById(UUID promotionId) {
        return promotionRepo.findById(promotionId).orElseThrow(() -> new ValidationException("promotion not found with given id"));
    }


    public String togglePromotion(UUID promotionId, StatusEnum status) {
        Promotion promotion = findPromotionById(promotionId);
        Coupon c = promotion.getCoupon();

        if (c.getStatus() == StatusEnum.DISABLED) throw new ValidationException("coupon is disabled");
        promotion.setStatus(status);
        promotionRepo.save(promotion);

        return ConstantMsg.OK;
    }
}

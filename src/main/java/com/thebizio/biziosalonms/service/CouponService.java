package com.thebizio.biziosalonms.service;

import com.thebizio.biziosalonms.dto.coupon.CouponCreateDto;
import com.thebizio.biziosalonms.dto.coupon.CouponDetailsDto;
import com.thebizio.biziosalonms.dto.coupon.CouponUpdateDto;
import com.thebizio.biziosalonms.entity.Coupon;
import com.thebizio.biziosalonms.enums.StatusEnum;
import com.thebizio.biziosalonms.exception.NotFoundException;
import com.thebizio.biziosalonms.exception.ValidationException;
import com.thebizio.biziosalonms.projection.coupon.CouponDetailPrj;
import com.thebizio.biziosalonms.projection.coupon.CouponListPrj;
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
public class CouponService {

    @Autowired
    private CouponRepo couponRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PromotionRepo promotionRepo;

    public CouponDetailsDto saveCoupon(CouponCreateDto createDto) {

        Coupon coupon = new Coupon();

        if (createDto.getStartDate().isAfter(createDto.getEndDate())){
            throw new ValidationException("Start date must be less than or equal to end date");
        }

        checkUniqueName(createDto.getName());

        coupon.setName(createDto.getName());
        coupon.setType(createDto.getCouponType());
        coupon.setStatus(StatusEnum.ENABLED);
        coupon.setValue(createDto.getValue());
        coupon.setStartDate(createDto.getStartDate());
        coupon.setEndDate(createDto.getEndDate());
        coupon.setMaxRedemptions(createDto.getMaxRedemptions());
        couponRepo.save(coupon);

        return modelMapper.map(coupon, CouponDetailsDto.class);
    }

    public Coupon findById(UUID id){
        return couponRepo.findById(id).orElseThrow(() -> new NotFoundException("coupon not found"));
    }

    public List<CouponListPrj> getAll(Optional<StatusEnum> status, Optional<String> name,
                                      Optional<LocalDateTime> startDate,
                                      Optional<LocalDateTime> endDate) {
        if (status.isPresent() && name.isPresent() && startDate.isPresent() && endDate.isPresent())
            return couponRepo.getAllByStatusAndNameAndStartDateAndEndDate(status.get(), name.get(), startDate.get(), endDate.get());
        if (status.isPresent() && name.isPresent())
            return couponRepo.getAllByStatusAndName(status.get(), name.get());
        if (status.isPresent() && startDate.isPresent())
            return couponRepo.getAllByStatusAndStartDate(status.get(), startDate.get());
        if (status.isPresent() && endDate.isPresent())
            return couponRepo.getAllByStatusAndEndDate(status.get(), endDate.get());
        if (status.isPresent())
            return couponRepo.getAllByStatus(status.get());
        if (name.isPresent())
            return couponRepo.getAllByName(name.get());
        if (startDate.isPresent())
            return couponRepo.getAllByStartDate(startDate.get());
        if (endDate.isPresent())
            return couponRepo.getAllByEndDate(endDate.get());
        return couponRepo.getAll();
    }


    private void checkUniqueName(String name) {
        if(couponRepo.existsByName(name)) throw new ValidationException("coupon name already exists");
    }

    private void checkUniqueNameAndIdIsNot(String name,UUID id) {
        if(couponRepo.existsByNameAndIdIsNot(name,id)) throw new ValidationException("coupon name already exists");
    }

    public CouponDetailPrj getById(UUID couponId) {
        return couponRepo.fetchById(couponId).orElseThrow(() -> new ValidationException("coupon not found with given id"));
    }

    public Coupon getCouponById(UUID couponId) {
        return couponRepo.findById(couponId).orElseThrow(() -> new ValidationException("coupon not found with given id"));
    }

    public void updateCoupon(UUID couponId, CouponUpdateDto updateDto) {

        Coupon coupon = getCouponById(couponId);

        checkUniqueNameAndIdIsNot(updateDto.getName(),couponId);
        coupon.setName(updateDto.getName());
        couponRepo.save(coupon);
    }

    private boolean checkCouponUsed(UUID couponId) {
        return promotionRepo.existsByCouponIdAndStatus(couponId, StatusEnum.ENABLED);
    }

    public String toggleCoupon(UUID couponId, StatusEnum status) {

        Coupon coupon = getCouponById(couponId);
        if (coupon.getStatus().equals(status)) throw new ValidationException("Coupon is already " + status.toString().toLowerCase());
        if (checkCouponUsed(couponId)) throw new ValidationException("promotion is enabled so coupon can't disabled");
        coupon.setStatus(status);
        couponRepo.save(coupon);
        return ConstantMsg.OK;
    }
}

package com.thebizio.biziosalonms.repo;

import com.thebizio.biziosalonms.entity.Coupon;
import com.thebizio.biziosalonms.entity.Promotion;
import com.thebizio.biziosalonms.enums.StatusEnum;
import com.thebizio.biziosalonms.projection.promotion.PromotionDetailPrj;
import com.thebizio.biziosalonms.projection.promotion.PromotionListPrj;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PromotionRepo extends JpaRepository<Promotion, UUID> {
    boolean existsByCode(String code);

    @Query("Select p.id as id, p.code as code, p.status as status, p.endDate as endDate, p.maxRedemptions as  maxRedemptions, p.timesRedeemed as timesRedeemed from Promotion p where p.id = :promoId")
    Optional<PromotionDetailPrj> fetchById(UUID promoId);

    @Query("Select sum(p.timesRedeemed) from Promotion p where p.coupon = :coupon")
    Integer sumByTimesRedeemedAndCoupon(Coupon coupon);

    @Query("Select p.id as id, p.code as code, p.status as status, p.endDate as endDate, p.maxRedemptions as  maxRedemptions," +
            " p.timesRedeemed as timesRedeemed from Promotion p where p.status = :status ORDER BY p.modified DESC")
    List<PromotionListPrj> getAllByStatus(StatusEnum status);

    @Query("Select p.id as id, p.code as code, p.status as status, p.endDate as endDate, p.maxRedemptions as  maxRedemptions," +
            " p.timesRedeemed as timesRedeemed from Promotion p where p.code = :code ORDER BY p.modified DESC")
    List<PromotionListPrj> getAllByCode(String code);

    @Query("Select p.id as id, p.code as code, p.status as status, p.endDate as endDate, p.maxRedemptions as  maxRedemptions," +
            " p.timesRedeemed as timesRedeemed from Promotion p where p.endDate = :endDate ORDER BY p.modified DESC")
    List<PromotionListPrj> getAllByEndDate(LocalDateTime endDate);

    @Query("Select p.id as id, p.code as code, p.status as status, p.endDate as endDate, p.maxRedemptions as  maxRedemptions," +
            " p.timesRedeemed as timesRedeemed from Promotion p where p.coupon.id = :couponId ORDER BY p.modified DESC")
    List<PromotionListPrj> getAllByCouponId(UUID couponId);

    @Query("Select p.id as id, p.code as code, p.status as status, p.endDate as endDate, p.maxRedemptions as  maxRedemptions," +
            " p.timesRedeemed as timesRedeemed from Promotion p ORDER BY p.modified DESC")
    List<PromotionListPrj> getAll();

    @Query("Select p.id as id, p.code as code, p.status as status, p.endDate as endDate, p.maxRedemptions as  maxRedemptions," +
            " p.timesRedeemed as timesRedeemed from Promotion p where p.status =:status and p.endDate =:endDate ORDER BY p.modified DESC")
    List<PromotionListPrj> getAllByStatusAndEndDate(StatusEnum status, LocalDateTime endDate);

    @Query("Select p.id as id, p.code as code, p.status as status, p.endDate as endDate, p.maxRedemptions as  maxRedemptions," +
            " p.timesRedeemed as timesRedeemed from Promotion p where p.status =:status and p.coupon.id =:couponId ORDER BY p.modified DESC")
    List<PromotionListPrj> getAllByStatusAndCouponId(StatusEnum status, UUID couponId);

    @Query("Select p.id as id, p.code as code, p.status as status, p.endDate as endDate, p.maxRedemptions as  maxRedemptions," +
            " p.timesRedeemed as timesRedeemed from Promotion p where p.status =:status and p.code =:code ORDER BY p.modified DESC")
    List<PromotionListPrj> getAllByStatusAndCode(StatusEnum status, String code);

    @Query("Select p.id as id, p.code as code, p.status as status, p.endDate as endDate, p.maxRedemptions as  maxRedemptions," +
            " p.timesRedeemed as timesRedeemed from Promotion p " +
            "where p.status =:status and p.code =:code and p.coupon.id =:couponId and p.endDate =:endDate ORDER BY p.modified DESC")
    List<PromotionListPrj> getAllByStatusAndCodeAndCouponIdAndEndDate(StatusEnum status, String code, UUID couponId, LocalDateTime endDate);
}

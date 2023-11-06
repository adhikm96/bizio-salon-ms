package com.thebizio.biziosalonms.repo;

import com.thebizio.biziosalonms.entity.Coupon;
import com.thebizio.biziosalonms.enums.StatusEnum;
import com.thebizio.biziosalonms.projection.coupon.CouponDetailPrj;
import com.thebizio.biziosalonms.projection.coupon.CouponListPrj;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CouponRepo extends JpaRepository<Coupon, UUID> {
    boolean existsByNameAndIdIsNot(String name, UUID id);

    boolean existsByName(String name);

    @Query("SELECT c.id as id, c.name as name, c.type as type, c.status as status, c.value as value, c.maxRedemptions as maxRedemptions, c.startDate as startDate, c.endDate as endDate FROM Coupon c ORDER BY c.modified DESC")
    List<CouponListPrj> getAll();

    @Query("SELECT c.id as id, c.name as name, c.type as type, c.value as value, c.status as status, c.startDate as startDate, c.endDate as endDate, c.maxRedemptions as maxRedemptions FROM Coupon c " +
            "WHERE c.status = :status ORDER BY c.modified DESC")
    List<CouponListPrj> getAllByStatus(StatusEnum status);

    @Query("SELECT c.id as id, c.name as name, c.type as type, c.status as status, c.value as value, c.maxRedemptions as maxRedemptions, c.startDate as startDate, c.endDate as endDate FROM Coupon c WHERE c.id = :couponId")
    Optional<CouponDetailPrj> fetchById(UUID couponId);

    @Query("SELECT c.id as id, c.name as name, c.type as type, c.value as value, c.status as status, c.startDate as startDate, c.endDate as endDate, c.maxRedemptions as maxRedemptions FROM Coupon c " +
            "WHERE c.name = :name ORDER BY c.modified DESC")
    List<CouponListPrj> getAllByName(String name);

    @Query("SELECT c.id as id, c.name as name, c.type as type, c.value as value, c.status as status, c.startDate as startDate, c.endDate as endDate, c.maxRedemptions as maxRedemptions FROM Coupon c " +
            "WHERE c.startDate = :startDate ORDER BY c.modified DESC")
    List<CouponListPrj> getAllByStartDate(LocalDateTime startDate);

    @Query("SELECT c.id as id, c.name as name, c.type as type, c.value as value, c.status as status, c.startDate as startDate, c.endDate as endDate, c.maxRedemptions as maxRedemptions FROM Coupon c " +
            "WHERE c.endDate = :endDate ORDER BY c.modified DESC")
    List<CouponListPrj> getAllByEndDate(LocalDateTime endDate);

    @Query("SELECT c.id as id, c.name as name, c.type as type, c.value as value, c.status as status, c.startDate as startDate, c.endDate as endDate, c.maxRedemptions as maxRedemptions FROM Coupon c " +
            "WHERE c.status = :status and c.name = :name ORDER BY c.modified DESC")
    List<CouponListPrj> getAllByStatusAndName(StatusEnum status, String name);

    @Query("SELECT c.id as id, c.name as name, c.type as type, c.value as value, c.status as status, c.startDate as startDate, c.endDate as endDate, c.maxRedemptions as maxRedemptions FROM Coupon c " +
            "WHERE c.status = :status and c.startDate = :startDate ORDER BY c.modified DESC")
    List<CouponListPrj> getAllByStatusAndStartDate(StatusEnum status, LocalDateTime startDate);

    @Query("SELECT c.id as id, c.name as name, c.type as type, c.value as value, c.status as status, c.startDate as startDate, c.endDate as endDate, c.maxRedemptions as maxRedemptions FROM Coupon c " +
            "WHERE c.status = :status and c.endDate = :endDate ORDER BY c.modified DESC")
    List<CouponListPrj> getAllByStatusAndEndDate(StatusEnum status, LocalDateTime endDate);

    @Query("SELECT c.id as id, c.name as name, c.type as type, c.value as value, c.status as status, c.startDate as startDate, c.endDate as endDate, c.maxRedemptions as maxRedemptions FROM Coupon c " +
            "WHERE c.status = :status and c.name = :name and c.startDate = :startDate and c.endDate = :endDate ORDER BY c.modified DESC")
    List<CouponListPrj> getAllByStatusAndNameAndStartDateAndEndDate(StatusEnum status, String name, LocalDateTime startDate, LocalDateTime endDate);
}

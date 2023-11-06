package com.thebizio.biziosalonms.repo;

import com.thebizio.biziosalonms.entity.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PromotionRepo extends JpaRepository<Promotion, UUID> {

    Optional<Promotion> findByCode(String promoCode);
}

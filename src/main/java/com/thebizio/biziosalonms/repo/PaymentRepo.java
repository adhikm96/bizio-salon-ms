package com.thebizio.biziosalonms.repo;

import com.thebizio.biziosalonms.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentRepo extends JpaRepository<Payment, UUID> {
}

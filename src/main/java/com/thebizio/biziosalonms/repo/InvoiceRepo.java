package com.thebizio.biziosalonms.repo;

import com.thebizio.biziosalonms.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InvoiceRepo extends JpaRepository<Invoice, UUID> {
}

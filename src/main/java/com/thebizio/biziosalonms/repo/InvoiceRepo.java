package com.thebizio.biziosalonms.repo;

import com.thebizio.biziosalonms.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.UUID;

public interface InvoiceRepo extends JpaRepository<Invoice, UUID> ,JpaSpecificationExecutor<Invoice> {
}

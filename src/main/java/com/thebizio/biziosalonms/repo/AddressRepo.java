package com.thebizio.biziosalonms.repo;

import com.thebizio.biziosalonms.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AddressRepo extends JpaRepository<Address, UUID> {
}

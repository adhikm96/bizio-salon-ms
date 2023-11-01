package com.thebizio.biziosalonms.service;

import com.thebizio.biziosalonms.entity.Branch;
import com.thebizio.biziosalonms.exception.NotFoundException;
import com.thebizio.biziosalonms.repo.BranchRepo;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BranchService {
    final BranchRepo branchRepo;

    public BranchService(BranchRepo branchRepo) {
        this.branchRepo = branchRepo;
    }

    public Branch findById(UUID id) {
        return branchRepo.findById(id).orElseThrow(() -> new NotFoundException("branch not found"));
    }
}

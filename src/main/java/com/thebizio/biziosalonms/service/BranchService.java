package com.thebizio.biziosalonms.service;

import com.thebizio.biziosalonms.dto.address.AddressDto;
import com.thebizio.biziosalonms.dto.branch.BranchCreateUpdateDto;
import com.thebizio.biziosalonms.dto.branch.BranchDetailDto;
import com.thebizio.biziosalonms.entity.Address;
import com.thebizio.biziosalonms.entity.Branch;
import com.thebizio.biziosalonms.enums.BranchStatusEnum;
import com.thebizio.biziosalonms.exception.NotFoundException;
import com.thebizio.biziosalonms.exception.ValidationException;
import com.thebizio.biziosalonms.repo.AddressRepo;
import com.thebizio.biziosalonms.repo.BranchRepo;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BranchService {
    final BranchRepo branchRepo;

    final ModelMapper modelMapper;

    final AddressRepo addressRepo;

    final WorkScheduleService workScheduleService;


    public BranchService(BranchRepo branchRepo, ModelMapper modelMapper, AddressRepo addressRepo, WorkScheduleService workScheduleService) {
        this.branchRepo = branchRepo;
        this.modelMapper = modelMapper;
        this.addressRepo = addressRepo;
        this.workScheduleService = workScheduleService;
    }

    public Branch findById(UUID id) {
        return branchRepo.findById(id).orElseThrow(() -> new NotFoundException("branch not found"));
    }

    public String updateStatus(UUID uId, BranchStatusEnum status) {
        Branch branch = findById(uId);
        if (branch.getStatus().equals(status)) throw new ValidationException("Branch status already " + status.toString().toLowerCase());
        branch.setStatus(status);
        branchRepo.save(branch);
        return ConstantMsg.OK;
    }

    public BranchDetailDto saveBranch(BranchCreateUpdateDto createDto) {
        Branch branch = new Branch();
        checkUniqueEmail(createDto.getEmail());
        branch.setStatus(BranchStatusEnum.OPENED); // making default as open
        setBranchFields(branch, createDto);
        return mapToDetailDto(branch);
    }

    public void updateBranch(UUID branchId, BranchCreateUpdateDto createDto) {
        Branch branch = findById(branchId);
        if(!branch.getEmail().equals(createDto.getEmail())) checkUniqueEmail(createDto.getEmail());
        setBranchFields(branch, createDto);
        branchRepo.save(branch);
    }

    private void checkUniqueEmail(String email) {
        if(branchRepo.existsByEmail(email)){
            throw new ValidationException("email already exists");
        }
    }

    private void setBranchFields(Branch branch, BranchCreateUpdateDto createDto) {
        branch.setName(createDto.getName());
        branch.setContactNo(createDto.getContactNo());
        branch.setEmail(createDto.getEmail());

        if(createDto.getAddress() != null)
            branch.setAddress(addressRepo.save(modelMapper.map(createDto.getAddress(), Address.class)));

        //        branch.setCompany(createDto.getCompanyId());

        if(createDto.getWorkScheduleId() != null)
            branch.setWorkSchedule(workScheduleService.findById(createDto.getWorkScheduleId()));
    }

    private BranchDetailDto mapToDetailDto(Branch branch) {
        BranchDetailDto detailDto = modelMapper.map(branch, BranchDetailDto.class);
        detailDto.setAddress(modelMapper.map(branch.getAddress(), AddressDto.class));
        return detailDto;
    }
}

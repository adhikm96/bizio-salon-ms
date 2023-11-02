package com.thebizio.biziosalonms.service;

import com.thebizio.biziosalonms.dto.address.AddressDto;
import com.thebizio.biziosalonms.dto.branch.BranchCreateUpdateDto;
import com.thebizio.biziosalonms.dto.branch.BranchDetailDto;
import com.thebizio.biziosalonms.dto.branch.BranchListDto;
import com.thebizio.biziosalonms.entity.Address;
import com.thebizio.biziosalonms.entity.Branch;
import com.thebizio.biziosalonms.enums.BranchStatusEnum;
import com.thebizio.biziosalonms.exception.NotFoundException;
import com.thebizio.biziosalonms.exception.ValidationException;
import com.thebizio.biziosalonms.projection.branch.BranchDetailPrj;
import com.thebizio.biziosalonms.repo.AddressRepo;
import com.thebizio.biziosalonms.repo.BranchRepo;
import com.thebizio.biziosalonms.specification.BranchSpecification;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BranchService {
    final BranchRepo branchRepo;

    final ModelMapper modelMapper;

    final AddressRepo addressRepo;

    final AddressService addressService;
    final WorkScheduleService workScheduleService;

    final BranchSpecification branchSpecification;

    final CompanyService companyService;

    public BranchService(BranchRepo branchRepo, ModelMapper modelMapper, AddressRepo addressRepo, AddressService addressService, WorkScheduleService workScheduleService, BranchSpecification branchSpecification, CompanyService companyService) {
        this.branchRepo = branchRepo;
        this.modelMapper = modelMapper;
        this.addressRepo = addressRepo;
        this.addressService = addressService;
        this.workScheduleService = workScheduleService;
        this.branchSpecification = branchSpecification;
        this.companyService = companyService;
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
        branchRepo.save(branch);
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

        if(branch.getAddress() == null) branch.setAddress(new Address());
        addressService.setAddressFields(branch.getAddress(), modelMapper.map(createDto, AddressDto.class));
        addressRepo.save(branch.getAddress());

        if (createDto.getCompanyId() != null)
            branch.setCompany(companyService.findById(createDto.getCompanyId()));

        if(createDto.getWorkScheduleId() != null)
            branch.setWorkSchedule(workScheduleService.findById(createDto.getWorkScheduleId()));
    }

    private BranchDetailDto mapToDetailDto(Branch branch) {
        BranchDetailDto detailDto = modelMapper.map(branch, BranchDetailDto.class);

        Address address = branch.getAddress();

        detailDto.setStreetAddress1(address.getStreetAddress1());
        detailDto.setStreetAddress2(address.getStreetAddress2());
        detailDto.setCity(address.getCity());
        detailDto.setZipcode(address.getZipcode());
        detailDto.setState(address.getState());
        detailDto.setCountry(address.getCountry());

        return detailDto;
    }

    public List<BranchListDto> list( Map<String, String> filters) {
        return branchRepo.findAll(branchSpecification.listWithFilter(filters)).stream().map(this::mapToListDto).collect(Collectors.toList());
    }

    BranchListDto mapToListDto(Branch branch) {
        BranchListDto listDto = modelMapper.map(branch, BranchListDto.class);
        listDto.setZipcode(branch.getAddress().getZipcode());
        return listDto;
    }

    public BranchDetailPrj fetchBranch(UUID branchId) {
        return branchRepo.fetchById(branchId).orElseThrow(() -> new NotFoundException("branch not found"));
    }
}

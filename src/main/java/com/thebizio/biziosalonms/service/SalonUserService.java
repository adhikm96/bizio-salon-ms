package com.thebizio.biziosalonms.service;

import com.thebizio.biziosalonms.dto.salon_user.SalonUserListDto;
import com.thebizio.biziosalonms.dto.salon_user.SalonUserUpdateDto;
import com.thebizio.biziosalonms.entity.User;
import com.thebizio.biziosalonms.enums.PaySchedule;
import com.thebizio.biziosalonms.enums.StatusEnum;
import com.thebizio.biziosalonms.exception.NotFoundException;
import com.thebizio.biziosalonms.exception.ValidationException;
import com.thebizio.biziosalonms.projection.salon_user.SalonUserDetailPrj;
import com.thebizio.biziosalonms.repo.SalonUserRepo;
import com.thebizio.biziosalonms.service.keycloak.UtilService;
import com.thebizio.biziosalonms.specification.SalonUserSpecification;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SalonUserService {

    final ModelMapper modelMapper;

    final BranchService branchService;

    final SalonUserRepo salonUserRepo;

    final WorkScheduleService workScheduleService;

    final UtilService utilService;

    public SalonUserService(ModelMapper modelMapper, BranchService branchService, SalonUserRepo salonUserRepo, WorkScheduleService workScheduleService, UtilService utilService) {
        this.modelMapper = modelMapper;
        this.branchService = branchService;
        this.salonUserRepo = salonUserRepo;
        this.workScheduleService = workScheduleService;
        this.utilService = utilService;
    }

    public List<SalonUserListDto> list(Optional<StatusEnum> status, Optional<String> email, Optional<String> empCode, Optional<String> empType, Optional<PaySchedule> paySchedule, Optional<UUID> branch, Optional<UUID> workSchedule) {
        // TO DO - check admin
        List<User> salonUsers = salonUserRepo.findAll(SalonUserSpecification.findWithFilter(status, email, empCode, empType, paySchedule, branch, workSchedule), Sort.by(Sort.Direction.DESC,"modified"));
        return salonUsers.stream().map(this::mapToListDto).collect(Collectors.toList());
    }

    public SalonUserListDto mapToListDto(User user) {
        return modelMapper.map(user, SalonUserListDto.class);
    }

    public User findById(UUID id) {
        return salonUserRepo.findById(id).orElseThrow(() -> new NotFoundException("salon user not found"));
    }

    public SalonUserDetailPrj fetchUser(UUID uId) {
        // TO DO - check for admin
        return salonUserRepo.findByUserId(uId).orElseThrow(() -> new NotFoundException("salon user not found"));
    }

    public User findAuthSalonUser(){
        return salonUserRepo.findByEmail(utilService.getAuthEmail()).orElseThrow(() -> new NotFoundException("User not found"));
    }

    public void updateUser(UUID uId, SalonUserUpdateDto dto) {
        // TO DO - check for admin
        User salonUser = findById(uId);

        if(!dto.getEmpCode().equals(salonUser.getEmpCode())) checkUniqueEmpCode(dto.getEmpCode());

        salonUser.setEmpCode(dto.getEmpCode());
        salonUser.setEmpType(dto.getEmpType());
        salonUser.setPaySchedule(dto.getPaySchedule());
        salonUser.setDesignation(dto.getDesignation());

        if(dto.getWorkScheduleId() != null)
            salonUser.setWorkSchedule(workScheduleService.findById(dto.getWorkScheduleId()));

        if(dto.getBranchId() != null)
            salonUser.setBranch(branchService.findById(dto.getBranchId()));

        salonUserRepo.save(salonUser);
    }

    private void checkUniqueEmpCode(String empCode) {
        if(salonUserRepo.existsByEmpCode(empCode)){
            throw new ValidationException("empCode already exists");
        }
    }

    public String toggleUser(UUID uId, StatusEnum status) {
        User salonUser = findById(uId);
        if (salonUser.getStatus().equals(status)) throw new ValidationException("Salon User is already "+status.toString().toLowerCase());
        salonUser.setStatus(status);
        salonUserRepo.save(salonUser);
        return ConstantMsg.OK;
    }
}

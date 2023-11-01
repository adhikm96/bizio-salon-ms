package com.thebizio.biziosalonms.service;

import com.thebizio.biziosalonms.dto.salon_user.SalonUserListDto;
import com.thebizio.biziosalonms.dto.salon_user.SalonUserUpdateDto;
import com.thebizio.biziosalonms.entity.CustomerUser;
import com.thebizio.biziosalonms.entity.User;
import com.thebizio.biziosalonms.enums.PaySchedule;
import com.thebizio.biziosalonms.enums.StatusEnum;
import com.thebizio.biziosalonms.exception.NotFoundException;
import com.thebizio.biziosalonms.exception.ValidationException;
import com.thebizio.biziosalonms.projections.salon_user.SalonUserDetailPrj;
import com.thebizio.biziosalonms.repo.SalonUserRepo;
import com.thebizio.biziosalonms.specification.SalonUserSpecification;
import org.modelmapper.ModelMapper;
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

    public SalonUserService(ModelMapper modelMapper, BranchService branchService, SalonUserRepo salonUserRepo, WorkScheduleService workScheduleService) {
        this.modelMapper = modelMapper;
        this.branchService = branchService;
        this.salonUserRepo = salonUserRepo;
        this.workScheduleService = workScheduleService;
    }

    public List<SalonUserListDto> list(Optional<StatusEnum> status, Optional<String> email, Optional<String> empCode, Optional<String> empType, Optional<PaySchedule> paySchedule, Optional<UUID> branch, Optional<UUID> workSchedule) {
        // TO DO - check admin
        List<User> salonUsers = salonUserRepo.findAll(SalonUserSpecification.findWithFilter(status, email, empCode, empType, paySchedule, branch, workSchedule));
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
        return salonUserRepo.findByUserId(uId);
    }

    public void updateUser(UUID uId, SalonUserUpdateDto dto) {
        // TO DO - check for admin
        User salonUser = findById(uId);

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

    public String toggleUser(UUID uId, StatusEnum status) {
        User salonUser = findById(uId);
        if (salonUser.getStatus().equals(status)) throw new ValidationException("Salon User is already "+status.toString().toLowerCase());
        salonUser.setStatus(status);
        salonUserRepo.save(salonUser);
        return ConstantMsg.OK;
    }
}

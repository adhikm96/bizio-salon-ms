package com.thebizio.biziosalonms.service;

import com.thebizio.biziosalonms.dto.salon_user.SalonUserListDto;
import com.thebizio.biziosalonms.entity.User;
import com.thebizio.biziosalonms.enums.PaySchedule;
import com.thebizio.biziosalonms.enums.StatusEnum;
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
public class SaloneUserService {

    final ModelMapper modelMapper;

    final SalonUserRepo salonUserRepo;

    public SaloneUserService(ModelMapper modelMapper, SalonUserRepo salonUserRepo) {
        this.modelMapper = modelMapper;
        this.salonUserRepo = salonUserRepo;
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
        return salonUserRepo.findById(id).orElseThrow(() -> new RuntimeException());
    }

    public SalonUserDetailPrj fetchUser(UUID uId) {
        // TO DO - check for admin
        return salonUserRepo.findByUserId(uId);
    }
}

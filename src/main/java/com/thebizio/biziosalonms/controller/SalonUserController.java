package com.thebizio.biziosalonms.controller;

import com.thebizio.biziosalonms.dto.ResponseMessageDto;
import com.thebizio.biziosalonms.dto.salon_user.SalonUserListDto;
import com.thebizio.biziosalonms.dto.salon_user.SalonUserUpdateDto;
import com.thebizio.biziosalonms.enums.PaySchedule;
import com.thebizio.biziosalonms.enums.StatusEnum;
import com.thebizio.biziosalonms.projection.salon_user.SalonUserDetailPrj;
import com.thebizio.biziosalonms.service.SalonUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/salon-users")
public class SalonUserController {

    final SalonUserService salonUserService;

    public SalonUserController(SalonUserService salonUserService) {
        this.salonUserService = salonUserService;
    }

    @GetMapping
    List<SalonUserListDto> listSalonUsers(
                @RequestParam Optional<StatusEnum> status,
                @RequestParam Optional<String> email,
                @RequestParam Optional<String> empCode,
                @RequestParam Optional<String> empType,
                @RequestParam Optional<PaySchedule> paySchedule,
                @RequestParam Optional<UUID> branch,
                @RequestParam Optional<UUID> workSchedule
            ) {
        return salonUserService.list(status, email, empCode, empType, paySchedule, branch, workSchedule);
    }

    @GetMapping("/{uId}")
    SalonUserDetailPrj fetchUser(@PathVariable UUID uId) {
        return salonUserService.fetchUser(uId);
    }

    @PutMapping("/{uId}")
    void updateSalonUser(@PathVariable UUID uId, @RequestBody @Valid SalonUserUpdateDto dto) {
        salonUserService.updateUser(uId, dto);
    }

    @PostMapping("/enable/{uId}")
    public ResponseEntity<ResponseMessageDto> enableUser(@PathVariable(name = "uId") UUID uId){
        return ResponseEntity.ok(new ResponseMessageDto(salonUserService.toggleUser(uId, StatusEnum.ENABLED)));
    }

    @PostMapping("/disable/{uId}")
    public ResponseEntity<ResponseMessageDto> disableUser(@PathVariable(name = "uId") UUID uId) {
        return ResponseEntity.ok(new ResponseMessageDto(salonUserService.toggleUser(uId, StatusEnum.DISABLED)));
    }

}

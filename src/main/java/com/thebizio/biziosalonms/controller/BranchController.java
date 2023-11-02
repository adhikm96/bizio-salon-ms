package com.thebizio.biziosalonms.controller;

import com.thebizio.biziosalonms.dto.ResponseMessageDto;
import com.thebizio.biziosalonms.dto.branch.BranchCreateUpdateDto;
import com.thebizio.biziosalonms.dto.branch.BranchDetailDto;
//import com.thebizio.biziosalonms.dto.salon_user.BranchListDto;
//import com.thebizio.biziosalonms.dto.salon_user.BranchUpdateDto;
import com.thebizio.biziosalonms.enums.BranchStatusEnum;
//import com.thebizio.biziosalonms.projection.salon_user.BranchDetailPrj;
import com.thebizio.biziosalonms.service.BranchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/branches")
public class BranchController {
    final BranchService branchService;

    public BranchController(BranchService branchService) {
        this.branchService = branchService;
    }

//    @GetMapping
//    List<BranchListDto> listBranches(
//            @RequestParam Optional<StatusEnum> status,
//            @RequestParam Optional<String> email,
//            @RequestParam Optional<String> empCode,
//            @RequestParam Optional<String> empType,
//            @RequestParam Optional<PaySchedule> paySchedule,
//            @RequestParam Optional<UUID> branch,
//            @RequestParam Optional<UUID> workSchedule
//    ) {
//        return branchService.list(status, email, empCode, empType, paySchedule, branch, workSchedule);
//    }

    @PostMapping
    BranchDetailDto createBranch(@RequestBody @Valid BranchCreateUpdateDto createDto) {
        return branchService.saveBranch(createDto);
    }


//    @GetMapping("/{branchId}")
//    BranchDetailPrj fetchBranch(@PathVariable UUID branchId) {
//        return branchService.fetchBranch(branchId);
//    }

    @PutMapping("/{branchId}")
    void updateBranch(@PathVariable UUID branchId, @RequestBody @Valid BranchCreateUpdateDto dto) {
        branchService.updateBranch(branchId, dto);
    }

    @PostMapping("/{branchId}/{status}")
    public ResponseEntity<ResponseMessageDto> updateStatus(@PathVariable(name = "branchId") UUID branchId, @PathVariable BranchStatusEnum status){
        return ResponseEntity.ok(new ResponseMessageDto(branchService.updateStatus(branchId, status)));
    }
}

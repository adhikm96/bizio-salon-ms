package com.thebizio.biziosalonms.controller;

import com.thebizio.biziosalonms.dto.ResponseMessageDto;
import com.thebizio.biziosalonms.dto.branch.BranchCreateUpdateDto;
import com.thebizio.biziosalonms.dto.branch.BranchDetailDto;
import com.thebizio.biziosalonms.dto.branch.BranchListDto;
import com.thebizio.biziosalonms.enums.BranchStatusEnum;
import com.thebizio.biziosalonms.projection.branch.BranchDetailPrj;
import com.thebizio.biziosalonms.service.BranchService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/branches")
public class BranchController {
    final BranchService branchService;

    public BranchController(BranchService branchService) {
        this.branchService = branchService;
    }

    @GetMapping
    List<BranchListDto> listBranches(@RequestParam Map<String, String> filters) {
        return branchService.list(filters);
    }

    @PostMapping
    BranchDetailDto createBranch(@RequestBody @Valid BranchCreateUpdateDto createDto) {
        return branchService.saveBranch(createDto);
    }


    @GetMapping("/{branchId}")
    BranchDetailPrj fetchBranch(@PathVariable UUID branchId) {
        return branchService.fetchBranch(branchId);
    }

    @PutMapping("/{branchId}")
    void updateBranch(@PathVariable UUID branchId, @RequestBody @Valid BranchCreateUpdateDto dto) {
        branchService.updateBranch(branchId, dto);
    }

    @PostMapping("/{branchId}/{status}")
    public ResponseMessageDto updateStatus(@PathVariable(name = "branchId") UUID branchId, @PathVariable BranchStatusEnum status){
        return new ResponseMessageDto(branchService.updateStatus(branchId, status));
    }
}

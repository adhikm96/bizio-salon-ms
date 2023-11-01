package com.thebizio.biziosalonms.controller;

import com.thebizio.biziosalonms.dto.Customer.CreateUpdateCustomerDto;
import com.thebizio.biziosalonms.dto.Customer.CustomerDetailDto;
import com.thebizio.biziosalonms.dto.ResponseMessageDto;
import com.thebizio.biziosalonms.enums.StatusEnum;
import com.thebizio.biziosalonms.projection.customer.CustomerDetailPrj;
import com.thebizio.biziosalonms.projection.customer.CustomerListPrj;
import com.thebizio.biziosalonms.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    ResponseEntity<List<CustomerListPrj>> getAll(@RequestParam Optional<String> email, @RequestParam Optional<String> mobile,
                                                 @RequestParam Optional<StatusEnum> status, @RequestParam Optional<String> zipCode) {
        return ResponseEntity.ok(customerService.getAllUser(email,mobile,status,zipCode));
    }

    @GetMapping("/{userId}")
    ResponseEntity<CustomerDetailPrj> getById(@PathVariable UUID userId) {
        return ResponseEntity.ok(customerService.getUserById(userId));
    }

    @PostMapping
    public ResponseEntity<CustomerDetailDto> create(@RequestBody @Valid CreateUpdateCustomerDto dto){
        return ResponseEntity.ok(customerService.createCustomer(dto));
    }

    @PostMapping("/{userId}")
    public ResponseEntity<CustomerDetailDto> update(@PathVariable(name = "userId") UUID userId,
                                                    @RequestBody @Valid CreateUpdateCustomerDto dto){
        return ResponseEntity.ok(customerService.updateCustomer(userId,dto));
    }

    @PostMapping("/enable/{userId}")
    public ResponseEntity<ResponseMessageDto> enableUser(@PathVariable(name = "userId") UUID userId){
        return ResponseEntity.ok(new ResponseMessageDto(customerService.toggleUser(userId, StatusEnum.ENABLED)));
    }

    @PostMapping("/disable/{userId}")
    public ResponseEntity<ResponseMessageDto> disableUser(@PathVariable(name = "userId") UUID userId) {
        return ResponseEntity.ok(new ResponseMessageDto(customerService.toggleUser(userId, StatusEnum.DISABLED)));
    }
}

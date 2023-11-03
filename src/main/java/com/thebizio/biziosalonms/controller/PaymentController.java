package com.thebizio.biziosalonms.controller;

import com.thebizio.biziosalonms.dto.payment.PaymentListDto;
import com.thebizio.biziosalonms.service.PaymentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/api/v1/payments")
@RestController
public class PaymentController {

    final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping
    List<PaymentListDto> listWithFilters(@RequestParam Map<String, String> filters) {
        return paymentService.listByFilters(filters);
    }
}

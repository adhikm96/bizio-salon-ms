package com.thebizio.biziosalonms.controller;

import com.thebizio.biziosalonms.dto.ResponseMessageDto;
import com.thebizio.biziosalonms.dto.appointment.AppointmentIdDto;
import com.thebizio.biziosalonms.dto.checkout.CheckoutPaymentDto;
import com.thebizio.biziosalonms.dto.checkout.CheckoutSessionDto;
import com.thebizio.biziosalonms.dto.checkout.CheckoutSessionResponseDto;
import com.thebizio.biziosalonms.dto.invoice.InvoiceDetailDto;
import com.thebizio.biziosalonms.dto.payment.PaymentDetailDto;
import com.thebizio.biziosalonms.service.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/checkout")
public class CheckoutController {

    @Autowired
    private CheckoutService checkoutService;

    @PostMapping("/start")
    public ResponseEntity<ResponseMessageDto> start(@RequestBody @Valid AppointmentIdDto dto){
        return ResponseEntity.ok(new ResponseMessageDto(checkoutService.startCheckout(dto)));
    }

    @PostMapping("/session")
    public ResponseEntity<CheckoutSessionResponseDto> session(@RequestBody @Valid CheckoutSessionDto dto){
        return ResponseEntity.ok(checkoutService.checkoutSession(dto));
    }

    @PostMapping("/session/callback")
    public ResponseEntity<ResponseMessageDto> sessionCallback(@RequestBody @Valid AppointmentIdDto dto){
        return ResponseEntity.ok(new ResponseMessageDto(checkoutService.checkoutSessionCallback(dto)));
    }

    @PostMapping
    public ResponseEntity<InvoiceDetailDto> checkout(@RequestBody @Valid CheckoutSessionDto dto){
        return ResponseEntity.ok(checkoutService.checkoutAppointment(dto));
    }

    @PostMapping("/payment")
    public ResponseEntity<PaymentDetailDto> payment(@RequestBody @Valid CheckoutPaymentDto dto){
        return ResponseEntity.ok(checkoutService.checkoutPayment(dto));
    }
}

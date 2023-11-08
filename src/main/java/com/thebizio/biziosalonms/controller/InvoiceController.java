package com.thebizio.biziosalonms.controller;

import com.thebizio.biziosalonms.dto.ResponseMessageDto;
import com.thebizio.biziosalonms.dto.customer.CreateUpdateCustomerDto;
import com.thebizio.biziosalonms.dto.invoice.InvoiceDetailDto;
import com.thebizio.biziosalonms.dto.invoice.InvoiceListDetailDto;
import com.thebizio.biziosalonms.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/invoices")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @GetMapping
    ResponseEntity<List<InvoiceListDetailDto>> getAll(@RequestParam Map<String, String> filters) {
        return ResponseEntity.ok(invoiceService.getAllInvoice(filters));
    }

    @GetMapping("/{invoiceId}")
    ResponseEntity<InvoiceDetailDto> getById(@PathVariable UUID invoiceId) {
        return ResponseEntity.ok(invoiceService.getInvoiceId(invoiceId));
    }

    @PostMapping("/{invoiceId}/pay")
    ResponseEntity<ResponseMessageDto> pay(@PathVariable UUID invoiceId) {
        return ResponseEntity.ok(new ResponseMessageDto(invoiceService.payInvoice(invoiceId)));
    }
}

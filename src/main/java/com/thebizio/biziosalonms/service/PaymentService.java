package com.thebizio.biziosalonms.service;

import com.thebizio.biziosalonms.dto.payment.PaymentListDto;
import com.thebizio.biziosalonms.entity.Payment;
import com.thebizio.biziosalonms.repo.PaymentRepo;
import com.thebizio.biziosalonms.specification.PaymentSpecification;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    final PaymentSpecification paymentSpecification;
    final PaymentRepo paymentRepo;

    final ModelMapper modelMapper;

    public PaymentService(PaymentSpecification paymentSpecification, PaymentRepo paymentRepo, ModelMapper modelMapper) {
        this.paymentSpecification = paymentSpecification;
        this.paymentRepo = paymentRepo;
        this.modelMapper = modelMapper;
    }

    public List<PaymentListDto> listByFilters(Map<String, String> filters) {
        return paymentRepo.findAll(paymentSpecification.withFilters(filters), Sort.by(Sort.Direction.DESC,"modified"))
                .stream()
                .map(this::mapToListDto)
                .collect(Collectors.toList());
    }

    PaymentListDto mapToListDto(Payment payment) {
        PaymentListDto listDto = modelMapper.map(payment, PaymentListDto.class);
        listDto.setInvoiceId(payment.getInvoice().getId());
        return listDto;
    }
}

package com.thebizio.biziosalonms.service;

import com.thebizio.biziosalonms.dto.payment.PaymentListDto;
import com.thebizio.biziosalonms.entity.Invoice;
import com.thebizio.biziosalonms.entity.Payment;
import com.thebizio.biziosalonms.enums.PaymentTypeEnum;
import com.thebizio.biziosalonms.repo.PaymentRepo;
import com.thebizio.biziosalonms.specification.PaymentSpecification;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepo paymentRepo;

    @Autowired
    private PaymentSpecification paymentSpecification;

    @Autowired
    private ModelMapper modelMapper;


    public Payment createPayment(PaymentTypeEnum paymentType, String paymentRef, Invoice invoice){
        Payment payment = new Payment();
        if (paymentType.equals(PaymentTypeEnum.CASH)) payment.setPaymentDate(LocalDate.now());
        payment.setPaymentType(paymentType);
        payment.setPaymentRef(paymentRef);
        payment.setInvoice(invoice);
        return paymentRepo.save(payment);
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

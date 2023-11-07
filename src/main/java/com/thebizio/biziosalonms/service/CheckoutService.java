package com.thebizio.biziosalonms.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thebizio.biziosalonms.dto.appointment.AppointmentIdDto;
import com.thebizio.biziosalonms.dto.checkout.*;
import com.thebizio.biziosalonms.dto.invoice.BillDto;
import com.thebizio.biziosalonms.dto.invoice.InvoiceDetailDto;
import com.thebizio.biziosalonms.dto.item.ItemListDto;
import com.thebizio.biziosalonms.dto.payment.PaymentDetailDto;
import com.thebizio.biziosalonms.entity.*;
import com.thebizio.biziosalonms.enums.AppointmentStatus;
import com.thebizio.biziosalonms.enums.InvoiceStatus;
import com.thebizio.biziosalonms.enums.PaymentTypeEnum;
import com.thebizio.biziosalonms.exception.ValidationException;
import com.thebizio.biziosalonms.repo.AppointmentRepo;
import com.thebizio.biziosalonms.repo.InvoiceRepo;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalTime;
import java.util.List;

@Service
public class CheckoutService {

    @Autowired
    private InvoiceRepo invoiceRepo;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private AppointmentRepo appointmentRepo;

    @Autowired
    private ItemService itemService;

    @Autowired
    private PromotionService promotionService;

    @Autowired
    private TaxScheduleService taxScheduleService;

    @Autowired
    private CalculateUtilService calculateUtilService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    @Lazy
    private InvoiceService invoiceService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private ModelMapper modelMapper;

    public String startCheckout(AppointmentIdDto dto) {
        Appointment appointment = appointmentService.fetchAppointmentById(dto.getAppointmentId());
        appointment.setStatus(AppointmentStatus.CHECKOUT);
        appointment.setEndTime(LocalTime.now());
        appointmentRepo.save(appointment);
        return ConstantMsg.OK;
    }

    @Transactional
    public CheckoutSessionResponseDto checkoutSession(CheckoutSessionDto dto) {
        Appointment appointment = appointmentService.fetchAppointmentById(dto.getAppointmentId());
        if (!appointment.getStatus().equals(AppointmentStatus.CHECKOUT)){
            appointment.setStatus(AppointmentStatus.CHECKOUT);
            appointment.setEndTime(LocalTime.now());
            appointmentRepo.save(appointment);
        }

        Promotion promotion = dto.getPromoCode() == null || dto.getPromoCode().equals("") ? null : promotionService.findByCode(dto.getPromoCode());
        if (promotion != null && !promotion.isValid()) throw new ValidationException("Invalid promo code");
        BillDto BillDto = invoiceService.calculateTaxAndDiscount(appointment,dto,promotion);

        CheckoutSessionResponseDto responseDto = new CheckoutSessionResponseDto();
        responseDto.setGrossTotal(BillDto.getGrossTotal());
        responseDto.setDiscount(BillDto.getDiscount());
        responseDto.setTaxes(BillDto.getTaxes());
        responseDto.setNetTotal(BillDto.getNetTotal());
        responseDto.setItems(modelMapper.map(BillDto.getPurchasedItem(),new TypeToken<List<ItemListDto>>(){}.getType()));
        responseDto.setDiscountStr(BillDto.getDiscountStr());

        try {
            responseDto.setTaxStr(objectMapper.writeValueAsString(BillDto.getTaxStringDto()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return  responseDto;
    }

    @Transactional
    public InvoiceDetailDto checkoutAppointment(CheckoutSessionDto dto) {
        Appointment appointment = appointmentService.fetchAppointmentById(dto.getAppointmentId());
        Invoice invoice = appointment.getInvoice() == null ? invoiceService.createInvoice(appointment,dto):invoiceService.updateInvoice(appointment,dto,appointment.getInvoice());
        return modelMapper.map(invoice, InvoiceDetailDto.class);
    }

    @Transactional
    public PaymentDetailDto checkoutPayment(CheckoutPaymentDto dto) {
        Invoice invoice = invoiceService.fetchById(dto.getInvoiceId());
        Appointment appointment = appointmentService.fetchAppointmentByInvoice(invoice);
        Payment payment = paymentService.createPayment(dto.getPaymentType(),null,invoice);

        if (payment.getPaymentType().equals(PaymentTypeEnum.CASH)){
            invoice.setStatus(InvoiceStatus.PAID);
            invoiceRepo.save(invoice);
        }

        appointment.setStatus(AppointmentStatus.COMPLETED);
        appointmentRepo.save(appointment);

        return modelMapper.map(payment, PaymentDetailDto.class);
    }

    public String checkoutSessionBack(AppointmentIdDto dto) {
        Appointment appointment =appointmentService.fetchAppointmentById(dto.getAppointmentId());
        if (appointment.getStatus().equals(AppointmentStatus.IN_PROGRESS)) throw new ValidationException("Appointment is already in-progress");

        appointment.setStatus(AppointmentStatus.IN_PROGRESS);
        appointment.setEndTime(null);
        appointmentRepo.save(appointment);
        return ConstantMsg.OK;
    }
}

package com.thebizio.biziosalonms.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thebizio.biziosalonms.dto.checkout.CheckoutSessionDto;
import com.thebizio.biziosalonms.dto.checkout.GrossTotalTaxDetailDto;
import com.thebizio.biziosalonms.dto.checkout.TaxStringDto;
import com.thebizio.biziosalonms.dto.invoice.BillDto;
import com.thebizio.biziosalonms.dto.tax_schedule_item.ItemTaxDetailDto;
import com.thebizio.biziosalonms.entity.*;
import com.thebizio.biziosalonms.enums.CouponTypeEnum;
import com.thebizio.biziosalonms.enums.InvoiceStatus;
import com.thebizio.biziosalonms.exception.NotFoundException;
import com.thebizio.biziosalonms.repo.AppointmentRepo;
import com.thebizio.biziosalonms.repo.InvoiceRepo;
import com.thebizio.biziosalonms.repo.TaxScheduleItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class InvoiceService {

    @Autowired
    private TaxScheduleService taxScheduleService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private PromotionService promotionService;

    @Autowired
    private TaxScheduleItemRepo taxScheduleItemRepo;

    @Autowired
    private CalculateUtilService calculateUtilService;

    @Autowired
    private AppointmentRepo appointmentRepo;

    @Autowired
    private CalculateUtilService calculatedUtilService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private InvoiceRepo invoiceRepo;

    public Invoice fetchById(UUID invoiceId){
        return invoiceRepo.findById(invoiceId).orElseThrow(() -> new NotFoundException("Invoice not found"));
    }

    public Invoice createInvoice(Appointment appointment, CheckoutSessionDto dto) {
        return setItem(appointment,dto,new Invoice());
    }

    public Invoice updateInvoice(Appointment appointment, CheckoutSessionDto dto,Invoice invoice) {
        return setItem(appointment,dto,invoice);
    }

    private Invoice setItem(Appointment appointment, CheckoutSessionDto dto,Invoice invoice) {
        BillDto BillDto = calculateTaxAndDiscount(appointment,dto);

        invoice.setBranch(BillDto.getBranch());
        invoice.setCustomerUser(appointment.getCustomerUser());
        invoice.setStatus(InvoiceStatus.UNPAID);
        invoice.setPostingDate(LocalDate.now());
        invoice.setCustomerName(appointment.getCustomerUser().getFirstName());
        invoice.setItems(BillDto.getPurchasedItem());
        invoice.setGrossTotal(BillDto.getGrossTotal());
        invoice.setDiscount(BillDto.getDiscount());
        invoice.setDiscountStr(BillDto.getDiscountStr());
        invoice.setTaxes(BillDto.getTaxes());
        try {
            invoice.setTaxStr(objectMapper.writeValueAsString(BillDto.getTaxStringDto()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        invoice.setNetTotal(BillDto.getNetTotal());
        invoiceRepo.save(invoice);

        appointment.setPurchases(BillDto.getPurchasedItem());
        appointment.setInvoice(invoice);
        appointmentRepo.save(appointment);

        return invoice;
    }


    public BillDto calculateTaxAndDiscount(Appointment appointment, CheckoutSessionDto dto) {
        Branch branch = appointment.getBranch();
        Double grossTotal = 0.0;
        Double discount = 0.0;
        String discountStr = null;
        Double taxes = 0.0;
        Double netTotal = 0.0;
        boolean isFullDiscount = false;
        Promotion promotion = dto.getPromoCode() == null || dto.getPromoCode().equals("") ? null : promotionService.findByCode(dto.getPromoCode());

        TaxStringDto taxStringDto = new TaxStringDto();
        TaxSchedule taxSchedule = taxScheduleService.getTaxScheduleOfBranch(branch);
        List<TaxScheduleItem> taxScheduleItems = taxSchedule == null ? new ArrayList<>() : taxSchedule.getTaxScheduleItems();

        List<Item> purchasedItem = new ArrayList<>();
        for (UUID itemId : dto.getItems()) {
            Item item = itemService.fetchItemById(itemId);
            purchasedItem.add(item);
            List<ItemTaxDetailDto> taxDetailList = new ArrayList<>(taxScheduleService.calculateTaxOfItem(taxSchedule, item, taxScheduleItems));
            for (ItemTaxDetailDto taxDetail : taxDetailList) {
                grossTotal += taxDetail.getPrice();
                taxes += taxDetail.getTaxCalculated();
                taxStringDto.getItemTaxes().add(taxDetail);
            }
        }

        List<GrossTotalTaxDetailDto> grossTotalTaxDetailList = new ArrayList<>(taxScheduleService.calculateTaxOfGrossTotal(taxSchedule, grossTotal, taxScheduleItems));
        for (GrossTotalTaxDetailDto taxDetail : grossTotalTaxDetailList) {
            taxes += taxDetail.getTaxCalculated();
            taxStringDto.getGrossTotalTaxes().add(taxDetail);
        }

        if (promotion != null) {
            Coupon coupon = promotion.getCoupon();
            if (coupon.getType().equals(CouponTypeEnum.AMOUNT)) {
                discount = (double) coupon.getValue();
                if (coupon.getValue() >= grossTotal) {
                    isFullDiscount = true;
                }
            } else {
                discount = coupon.getValue() * (grossTotal) / 100;
                if (coupon.getValue() == 100d) {
                    isFullDiscount = true;
                }
            }
            discount = calculateUtilService.roundTwoDigits(discount);
            discountStr = "{\""+promotion.getCode()+"\":"+discount+"}";
            promotionService.incrementPromoCodeCounter(promotion);
        }

        netTotal = isFullDiscount ? 0.0 : calculateUtilService.roundTwoDigits(grossTotal + taxes - discount);


        BillDto invoiceBillDto = new BillDto();
        invoiceBillDto.setGrossTotal(grossTotal);
        invoiceBillDto.setNetTotal(netTotal);
        invoiceBillDto.setDiscount(discount);
        invoiceBillDto.setDiscountStr(discountStr);
        invoiceBillDto.setTaxes(taxes);
        invoiceBillDto.setTaxStringDto(taxStringDto);
        invoiceBillDto.setPurchasedItem(purchasedItem);
        invoiceBillDto.setBranch(branch);

        return invoiceBillDto;
    }

}

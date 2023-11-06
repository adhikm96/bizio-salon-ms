package com.thebizio.biziosalonms.service;

import com.thebizio.biziosalonms.enums.BranchStatusEnum;
import com.thebizio.biziosalonms.enums.PaymentTypeEnum;
import com.thebizio.biziosalonms.enums.StatusEnum;
import com.thebizio.biziosalonms.exception.ValidationException;
import org.keycloak.common.util.SecretGenerator;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.UUID;

@Service
public class StrUtil {
    public BranchStatusEnum getEnumFrom(String str) {
        try {
            return BranchStatusEnum.valueOf(str);
        }catch (IllegalArgumentException exception) {
            throw new ValidationException("incorrect value for branch status");
        }
    }

    public StatusEnum getStatusEnumFrom(String str) {
        try {
            return StatusEnum.valueOf(str);
        }catch (IllegalArgumentException exception) {
            throw new ValidationException("incorrect value for status");
        }
    }

    public PaymentTypeEnum getPaymentTypeFrom(String str) {
        try {
            return PaymentTypeEnum.valueOf(str);
        }catch (IllegalArgumentException exception) {
            throw new ValidationException("incorrect value for payment type");
        }
    }

    public UUID parsedUUID(String uuid) {
        try {
            return UUID.fromString(uuid);
        }catch (IllegalArgumentException exception) {
            throw new ValidationException("incorrect uuid");
        }
    }

    public LocalDate parsedLocalDate(String localDate) {
        try {
            return LocalDate.parse(localDate, DateTimeFormatter.ISO_LOCAL_DATE);
        }catch (DateTimeParseException exception) {
            throw new ValidationException("incorrect date");
        }
    }

    private static final String ALPHANUMERIC_CHARS = "@#$ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public String generateRandomPromocodeString(){
        return SecretGenerator.getInstance().randomString(6).toUpperCase();
    }
}

package com.thebizio.biziosalonms.service;

import com.thebizio.biziosalonms.enums.BranchStatusEnum;
import com.thebizio.biziosalonms.enums.StatusEnum;
import com.thebizio.biziosalonms.exception.ValidationException;
import org.springframework.stereotype.Service;

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
}

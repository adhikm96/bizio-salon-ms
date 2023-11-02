package com.thebizio.biziosalonms.service;

import com.thebizio.biziosalonms.enums.BranchStatusEnum;
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
}

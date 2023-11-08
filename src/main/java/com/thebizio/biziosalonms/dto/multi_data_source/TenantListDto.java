package com.thebizio.biziosalonms.dto.multi_data_source;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TenantListDto {
    private UUID id;

    private String tenantId;

    private String url;
    private String appCode;
    private String orgCode;

    private String username;

    private String password;

    private String driverClassName;
}

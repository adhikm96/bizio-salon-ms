package com.thebizio.biziosalonms.service;

import com.thebizio.biziosalonms.dto.multi_data_source.TenantListDto;

import java.util.List;

public interface TenantService {
    public List<TenantListDto> fetchTenants();
}

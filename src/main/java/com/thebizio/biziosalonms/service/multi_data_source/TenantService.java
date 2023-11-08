package com.thebizio.biziosalonms.service.multi_data_source;

import com.thebizio.biziosalonms.dto.multi_data_source.TenantListDto;

import java.util.List;

public interface TenantService {
    public List<TenantListDto> fetchTenants();
}

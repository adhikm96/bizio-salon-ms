package com.thebizio.biziosalonms.service.multi_data_source;

import com.thebizio.biziosalonms.dto.multi_data_source.TenantListDto;
import com.thebizio.biziosalonms.service.crypto.CryptoService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.*;

@Service
@Slf4j
public class MultiDataSourceHolder {

    final CryptoService cryptoService;

    @Setter
    private MultiRoutingDataSource multiRoutingDataSource;

    final TenantService tenantService;

    private final Map<Object, Object> targetDataSources = new HashMap<>();

    public MultiDataSourceHolder(CryptoService cryptoService, TenantService tenantService) {
        this.cryptoService = cryptoService;
        this.tenantService = tenantService;
    }

    public Map<Object, Object> getDataSourceMap() {
        return targetDataSources;
    }

    public Object getDS(String key) {
        return targetDataSources.get(key);
    }

    public void putDS(String key, DataSource dataSource) {
        targetDataSources.put(key, dataSource);
    }

    public void loadDBs() {

        List<TenantListDto> tenants = tenantService.fetchTenants();

        assert tenants != null && !tenants.isEmpty();

        for (TenantListDto tenant :
                tenants) {
            DataSource ds = DBUtil.getDataSource(tenant.getUrl(), tenant.getUsername(), tenant.getPassword());
            putDS(tenant.getOrgCode(), ds);
//            dbMigrateService.migrate(ds); // added this for testing on local
        }

        log.info("~~~~~~~~~~~~~~~~~~~~~~~");
        log.info("~~~~ Tenants loaded ~~~");
        log.info("~~~~~~~~~~~~~~~~~~~~~~~");
    }

}

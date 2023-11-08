package com.thebizio.biziosalonms.utils;

import com.thebizio.biziosalonms.dto.multi_data_source.TenantListDto;
import com.thebizio.biziosalonms.service.TenantService;
import com.thebizio.biziosalonms.service.flyway.DBMigrateService;
import com.thebizio.biziosalonms.service.multi_data_source.DBUtil;
import com.thebizio.biziosalonms.testcontaines.BaseTestContainer;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@Profile("test")
public class TenantServiceImplMock implements TenantService {

    final DBMigrateService dbMigrateService;
    final DBUtil dbUtil;

    public TenantServiceImplMock(DBMigrateService dbMigrateService, DBUtil dbUtil) {
        this.dbMigrateService = dbMigrateService;
        this.dbUtil = dbUtil;
    }

    @Override
    public List<TenantListDto> fetchTenants() {
        // migrating
        dbMigrateService.migrate(dbUtil.getDS(
                BaseTestContainer.tenant.getUrl(),
                BaseTestContainer.tenant.getUsername(),
                BaseTestContainer.tenant.getPassword()
        ));

        // returning as tenants array
        return Collections.singletonList(BaseTestContainer.tenant);
    }
}

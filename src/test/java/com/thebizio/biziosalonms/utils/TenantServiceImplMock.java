package com.thebizio.biziosalonms.utils;

import com.thebizio.biziosalonms.dto.multi_data_source.TenantListDto;
import com.thebizio.biziosalonms.service.multi_data_source.TenantService;
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

    public TenantServiceImplMock(DBMigrateService dbMigrateService) {
        this.dbMigrateService = dbMigrateService;
    }


    @Override
    public List<TenantListDto> fetchTenants() {
        // migrating
        dbMigrateService.migrate(DBUtil.getDataSource(
                BaseTestContainer.tenant.getUrl(),
                BaseTestContainer.tenant.getUsername(),
                BaseTestContainer.tenant.getPassword()
        ));

        // returning as tenants array
        return Collections.singletonList(BaseTestContainer.tenant);
    }
}

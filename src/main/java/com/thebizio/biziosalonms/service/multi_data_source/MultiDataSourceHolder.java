package com.thebizio.biziosalonms.service.multi_data_source;

import com.thebizio.biziosalonms.dto.multi_data_source.TenantListDto;
import com.thebizio.biziosalonms.service.crypto.CryptoService;
import com.thebizio.biziosalonms.service.flyway.DBMigrateService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.specimpl.MultivaluedMapImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class MultiDataSourceHolder {
    final DBUtil util;

    final RestTemplate restTemplate;

    final CryptoService cryptoService;

    @Setter
    private MultiRoutingDataSource multiRoutingDataSource;

    final DBMigrateService dbMigrateService;

    private final Map<Object, Object> targetDataSources = new HashMap<>();

    @Value("${tenant-service-url}")
    private String TENANT_SERVICE_URL;

    public MultiDataSourceHolder(DBUtil util, RestTemplate restTemplate, CryptoService cryptoService, DBMigrateService dbMigrateService) {
        this.util = util;
        this.restTemplate = restTemplate;
        this.cryptoService = cryptoService;
        this.dbMigrateService = dbMigrateService;
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
        List<TenantListDto> tenants = restTemplate.exchange(
                getTenantListUrl(),
                HttpMethod.GET,
                new HttpEntity<>(new MultivaluedMapImpl<>()),
                new ParameterizedTypeReference<List<TenantListDto>>() {
                }
        ).getBody();

        assert tenants != null && !tenants.isEmpty();

        for (TenantListDto tenant :
                tenants) {
            DataSource ds = util.getDS(tenant.getUrl(), tenant.getUsername(), cryptoService.decrypt(tenant.getPassword()));
            putDS(tenant.getOrgCode(), ds);
            dbMigrateService.migrate(ds);
        }

        log.info("~~~~~~~~~~~~~~~~~~~~~~~");
        log.info("Tenants loaded & migrated");
        log.info("~~~~~~~~~~~~~~~~~~~~~~~");
    }

    private String getTenantListUrl() {
        return TENANT_SERVICE_URL + "/api/v1/internal/tenants";
    }
}

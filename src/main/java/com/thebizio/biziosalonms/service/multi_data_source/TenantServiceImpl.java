package com.thebizio.biziosalonms.service.multi_data_source;

import com.thebizio.biziosalonms.dto.multi_data_source.TenantListDto;
import com.thebizio.biziosalonms.service.crypto.CryptoService;
import com.thebizio.biziosalonms.service.multi_data_source.TenantService;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TenantServiceImpl implements TenantService {

    @Value(("${keycloak.auth-server-url}"))
    String bzAdminServerUrl;

    @Value("${tenant-service-url}")
    private String TENANT_SERVICE_URL;

    @Value(("${bz-admin-kc-realm}"))
    String kcAdminRealm;

    @Value(("${bz-admin-kc-client}"))
    String bzAdminClient;

    @Value("${bz-admin-kc-user}")
    private String tenantKcAdminUser;

    @Value("${bz-admin-kc-password}")
    private String tenantKcAdminPass;

    @Value("${x-priv-pwd}")
    private String xPrivatePassword;

    public final String X_PRIV_PWD = "X-PRIV-PWD";

    final RestTemplate restTemplate;

    final CryptoService cryptoService;

    @Value(("${salon-app-code}"))
    private String SALON_APP_CODE;

    public TenantServiceImpl(RestTemplate restTemplate, CryptoService cryptoService) {
        this.restTemplate = restTemplate;
        this.cryptoService = cryptoService;
    }

    private String getToken() {

        Keycloak tenantKcAdmin = KeycloakBuilder.builder()
                .realm(kcAdminRealm)
                .clientId(bzAdminClient)
                .serverUrl(bzAdminServerUrl)
                .username(tenantKcAdminUser)
                .password(tenantKcAdminPass)
                .build();

        return "Bearer " + tenantKcAdmin.tokenManager().getAccessTokenString();
    }

    public List<TenantListDto> fetchTenants() {
        return fetchViaRestCall()
                .stream()
                .peek(tenantListDto -> cryptoService.decrypt(tenantListDto.getPassword()))
                .collect(Collectors.toList());
    }

    private List<TenantListDto> fetchViaRestCall() {
        HttpHeaders headers = new HttpHeaders();

        headers.put("Authorization", Collections.singletonList(getToken()));
        headers.put(X_PRIV_PWD, Collections.singletonList(xPrivatePassword));

        return restTemplate.exchange(
                getTenantListUrl(),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<List<TenantListDto>>() {
                }
        ).getBody();
    }

    private String getTenantListUrl() {
        return TENANT_SERVICE_URL + "/api/v1/internal/tenants?appCode=" + SALON_APP_CODE;
    }

}

package com.thebizio.biziosalonms.testcontaines;

import com.thebizio.biziosalonms.dto.multi_data_source.TenantListDto;
import dasniko.testcontainers.keycloak.KeycloakContainer;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.*;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.ws.rs.core.Response;
import java.util.*;

public class BaseTestContainer {

    private static String DB_NAME = "bz-salon-test" + new Random().nextInt(100000);
    public static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine")
            .withUsername("postgres")
            .withPassword("postgres")
            .withDatabaseName(DB_NAME);

    public static KeycloakContainer keycloak = new KeycloakContainer("jboss/keycloak")
            .withEnv("DB_VENDOR", "h2");

    public static Keycloak keycloakAdminClient = null;

    public static String KEYCLOAK_REALM = "test-realm";
    public static String KEYCLOAK_RESOURCE = "test-client";
    public static TenantListDto tenant;
    public static final String BZ_SALON = "BZ-Salon";

    public static final String ORG_CODE = "org-code-1";

    static {
        keycloak.start();
        postgres.start();

        keycloakAdminClient = KeycloakBuilder.builder()
                .serverUrl(keycloak.getAuthServerUrl())
                .realm("master")
                .clientId("admin-cli")
                .username(keycloak.getAdminUsername())
                .password(keycloak.getAdminPassword())
                .build();

        RealmRepresentation realmRepresentation = new RealmRepresentation();
        realmRepresentation.setRealm(KEYCLOAK_REALM);
        realmRepresentation.setEnabled(true);
        realmRepresentation.setId(KEYCLOAK_REALM);
        keycloakAdminClient.realms().create(realmRepresentation);

        testClientCreation();

        tenant = new TenantListDto(
                UUID.randomUUID(),
                DB_NAME,
                postgres.getJdbcUrl(),
                BZ_SALON,
                ORG_CODE,
                postgres.getUsername(),
                postgres.getPassword(),
                postgres.getDriverClassName()
        );
    }

    private static void testClientCreation() {
        // creating a client in master for later user
        ClientRepresentation clientRepresentation = new ClientRepresentation();
        clientRepresentation.setClientId(KEYCLOAK_RESOURCE);
        clientRepresentation.setPublicClient(true);
        clientRepresentation.setEnabled(true);
        clientRepresentation.setDirectAccessGrantsEnabled(true);
        clientRepresentation.setProtocol("openid-connect");
        if(clientRepresentation.getAttributes() == null) {
            clientRepresentation.setAttributes(new HashMap<>());
        }
        clientRepresentation.getAttributes().put("access.token.lifespan", String.valueOf(3600 * 12)); // 12 hrs

        Response res = keycloakAdminClient.realm(KEYCLOAK_REALM).clients().create(clientRepresentation);
        if(res.getStatus() >= 400) System.out.println(res.readEntity(ErrorRepresentation.class).getErrorMessage());
    }

    @DynamicPropertySource
    public static void configureProperties(DynamicPropertyRegistry registry) {

        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);

        registry.add("keycloak.realm", () -> KEYCLOAK_REALM);
        registry.add("keycloak.auth-server-url", keycloak::getAuthServerUrl);
        registry.add("keycloak.resource",() -> KEYCLOAK_RESOURCE);

        registry.add("bz-admin-kc-user",() -> keycloak.getAdminUsername());
        registry.add("bz-admin-kc-password",() -> keycloak.getAdminPassword());
        registry.add("bz-admin-kc-client",() -> KEYCLOAK_RESOURCE);

        // below is test key - works only for version v2
        registry.add("captcha-secret-key",() -> "6LeIxAcTAAAAAGG-vFI1TnRWxMZNFuojJ4WifJWe");
        registry.add("stripe-secret-key",() -> "sk_test_4eC39HqLyjWDarjtT1zdp7dc");

    }
}

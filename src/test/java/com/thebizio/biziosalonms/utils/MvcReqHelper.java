package com.thebizio.biziosalonms.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thebizio.biziosalonms.entity.User;
import com.thebizio.biziosalonms.service.flyway.DBMigrateService;
import com.thebizio.biziosalonms.service.multi_data_source.MultiDataSourceHolder;
import com.thebizio.biziosalonms.testcontaines.BaseTestContainer;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.ErrorRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import javax.servlet.http.Cookie;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static com.thebizio.biziosalonms.testcontaines.BaseTestContainer.*;

@Service
public class MvcReqHelper {

    private static final String ORG_CODE_COOKIE_NAME = "org-code";

    @Autowired
    MultiDataSourceHolder multiDataSourceHolder;

    @Value("${x-priv-pwd}")
    String xPrivPwd;

    public static String DEFAULT_PASSWORD = "password";

    @Autowired
    private ObjectMapper objectMapper;

    String getToken(User user) {
        if(user.getUsername() == null)  return "Bearer " + UUID.randomUUID();
        demoUserCreation(user);
        Keycloak bzUserAdminClient =  KeycloakBuilder.builder()
                .serverUrl(keycloak.getAuthServerUrl())
                .realm(KEYCLOAK_REALM)
                .clientId(KEYCLOAK_RESOURCE)
                .username(user.getUsername())
                .password(DEFAULT_PASSWORD)
                .build();

        return "Bearer " + bzUserAdminClient.tokenManager().getAccessTokenString();
    }

    private static void demoUserCreation(User user) {

        if(!keycloakAdminClient.realm(KEYCLOAK_REALM).users().search(user.getUsername(), true).isEmpty()) {
            return;
        }

        UserRepresentation userRepresentation = new UserRepresentation();

        userRepresentation.setUsername(user.getUsername());
        userRepresentation.setEmail(user.getEmail());
        userRepresentation.setEnabled(true);

        // setting password
        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setValue(DEFAULT_PASSWORD);
        credentialRepresentation.setType("password");
        credentialRepresentation.setTemporary(false);

        if(userRepresentation.getRealmRoles() == null) {
            userRepresentation.setRealmRoles(new ArrayList<>());
        }

        List<CredentialRepresentation> credentialRepresentations = new ArrayList<>();
        credentialRepresentations.add(credentialRepresentation);

        userRepresentation.setCredentials(Collections.singletonList(credentialRepresentation));

        // User Created in Keycloak
        Response response = keycloakAdminClient.realm(KEYCLOAK_REALM).users().create(userRepresentation);

        if(response.getStatus() >= 400) System.out.println(response.readEntity(ErrorRepresentation.class).getErrorMessage());

    }

    public MockHttpServletRequestBuilder setUp(MockHttpServletRequestBuilder builder, User user) {
        return builder.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .header("Authorization", getToken(user))
                .cookie(new Cookie(ORG_CODE_COOKIE_NAME, ORG_CODE));
    }

    public MockHttpServletRequestBuilder setUp(MockHttpServletRequestBuilder builder, Object body, User user)
            throws JsonProcessingException {
        return builder.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body))
                .header("Authorization", getToken(user))
                .cookie(new Cookie(ORG_CODE_COOKIE_NAME, ORG_CODE));
    }

    public MockHttpServletRequestBuilder setUpWithoutToken(MockHttpServletRequestBuilder builder) {
        return builder.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
    }

    public MockHttpServletRequestBuilder setUpWithoutToken(MockHttpServletRequestBuilder builder, Object body)
            throws JsonProcessingException {
        return builder.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body));
    }

    public MockHttpServletRequestBuilder setUpWithoutToken(MockHttpServletRequestBuilder builder, Object body, String sigHeader)
            throws JsonProcessingException {
        return builder.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body))
                .header("stripe-signature",sigHeader);
    }
}

package com.thebizio.biziosalonms.service.keycloak;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class UtilService {

	public AccessToken getKeycloakPrincipal() {
		return ((KeycloakPrincipal) (SecurityContextHolder.getContext().getAuthentication().getPrincipal()))
				.getKeycloakSecurityContext().getToken();
	}

	public String getAuthUserName() {
		return getKeycloakPrincipal().getPreferredUsername();
	}

	public Principal getPrincipal() {
		return (Principal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

	public AccessToken getToken() {
		KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal<KeycloakSecurityContext>) getPrincipal();
		return principal.getKeycloakSecurityContext().getToken();
	}

}

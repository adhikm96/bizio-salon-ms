package com.thebizio.biziosalonms.service.keycloak;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;


public abstract class KeycloakClientService  {

	private static final String DEFAULT_PASSWORD = "bizio";
	protected String keycloakRealm;
	public abstract Keycloak getKeycloak();

	public RealmResource getRealm() {
		return getKeycloak().realm(keycloakRealm);
	}

	public List<RoleRepresentation> getRolesList() {
		return getRolesResource().list();
	}

	public RolesResource getRolesResource() {
		return getRealm().roles();
	}

	public List<UserRepresentation> getUsersList() {
		return getRealm().users().list();
	}

	public UsersResource getUsersResource() {
		return getRealm().users();
	}

}

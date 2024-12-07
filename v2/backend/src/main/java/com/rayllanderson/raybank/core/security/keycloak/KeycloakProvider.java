package com.rayllanderson.raybank.core.security.keycloak;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
@ConditionalOnProperty(name = "oauth.provider-name", havingValue = "keycloak")
public class KeycloakProvider {

    private Keycloak keycloak;

    @Value("${keycloak.server-url}")
    public String serverURL;
    @Value("${keycloak.realm}")
    public String realm;
    @Value("${keycloak.credentials.client-id}")
    public String clientID;
    @Value("${keycloak.credentials.client-secret}")
    public String clientSecret;

    public Keycloak getInstance() {
        if (Objects.isNull(keycloak)) {
            this.keycloak = KeycloakBuilder.builder()
                    .realm(realm)
                    .serverUrl(serverURL)
                    .clientId(clientID)
                    .clientSecret(clientSecret)
                    .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                    .build();
        }
        return keycloak;
    }

    public RealmResource getRealmInstance() {
        return getInstance().realm(realm);
    }
}
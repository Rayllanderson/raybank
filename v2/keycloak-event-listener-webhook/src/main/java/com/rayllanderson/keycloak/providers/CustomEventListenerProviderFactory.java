package com.rayllanderson.keycloak.providers;

import org.jboss.logging.Logger;
import org.keycloak.Config;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventListenerProviderFactory;
import org.keycloak.events.EventType;
import org.keycloak.events.admin.OperationType;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.keycloak.events.EventType.CODE_TO_TOKEN;
import static org.keycloak.events.EventType.LOGIN;
import static org.keycloak.events.EventType.LOGIN_ERROR;
import static org.keycloak.events.EventType.LOGOUT;
import static org.keycloak.events.EventType.REFRESH_TOKEN;
import static org.keycloak.events.EventType.REGISTER_ERROR;
import static org.keycloak.events.EventType.SEND_RESET_PASSWORD;
import static org.keycloak.events.EventType.valueOf;

public class CustomEventListenerProviderFactory implements EventListenerProviderFactory {

    private Set<OperationType> excludedAdminOperations;
    private Set<EventType> eventBlacklist = new HashSet<>(Arrays.asList(
            LOGIN,
            LOGOUT,
            SEND_RESET_PASSWORD,
            REFRESH_TOKEN,
            CODE_TO_TOKEN,
            REGISTER_ERROR,
            LOGIN_ERROR
    ));

    @Override
    public EventListenerProvider create(KeycloakSession session) {
        return new CustomEventListenerProvider(eventBlacklist, excludedAdminOperations);
    }

    @Override
    public void init(Config.Scope config) {
        String[] excludes = config.getArray("exclude-events");
        if (excludes != null) {
            eventBlacklist = new HashSet<>();
            for (String e : excludes) {
                eventBlacklist.add(valueOf(e));
            }
        }

        String[] excludesOperations = config.getArray("excludesOperations");
        if (excludesOperations != null) {
            excludedAdminOperations = new HashSet<>();
            for (String e : excludesOperations) {
                excludedAdminOperations.add(OperationType.valueOf(e));
            }
        }
    }


    @Override
    public void postInit(KeycloakSessionFactory factory) {
    }

    @Override
    public void close() {
    }

    @Override
    public String getId() {
        return "ray-webhook";
    }

}

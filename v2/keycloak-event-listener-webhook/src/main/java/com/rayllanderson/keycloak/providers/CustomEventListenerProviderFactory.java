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
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CustomEventListenerProviderFactory implements EventListenerProviderFactory {

    private static final Logger log = Logger.getLogger(CustomEventListenerProviderFactory.class);
    private Set<OperationType> excludedAdminOperations;
    private Set<EventType> eventBlacklist = new HashSet<>(Arrays.asList(
            EventType.LOGIN,
            EventType.LOGOUT,
            EventType.SEND_RESET_PASSWORD,
            EventType.REFRESH_TOKEN
    ));

    private List<String> serverUris;

    @Override
    public EventListenerProvider create(KeycloakSession session) {
        return new CustomEventListenerProvider(eventBlacklist, excludedAdminOperations, serverUris, null, null);
    }

    @Override
    public void init(Config.Scope config) {
        String[] excludes = config.getArray("exclude-events");
        if (excludes != null) {
            eventBlacklist = new HashSet<>();
            for (String e : excludes) {
                eventBlacklist.add(EventType.valueOf(e));
            }
        }

        String[] excludesOperations = config.getArray("excludesOperations");
        if (excludesOperations != null) {
            excludedAdminOperations = new HashSet<>();
            for (String e : excludesOperations) {
                excludedAdminOperations.add(OperationType.valueOf(e));
            }
        }

        String webhookUriString = System.getenv("WEBHOOK_URIS");
        log.info("Picked up environment variable WEBHOOK_URIS: " + webhookUriString);

        if (webhookUriString == null) {
            log.info("ServerURI: Using default webhook URI http://raybank:8080/api/v1/users/register. Configure it with env WEBHOOK_URIS");
            serverUris = Collections.singletonList("http://raybank:8080/api/v1/users/register");
        } else {
            log.info("ServerURI: Using env webhook URI " + webhookUriString);
            serverUris = Collections.singletonList(webhookUriString);
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

package com.rayllanderson.keycloak.providers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jboss.logging.Logger;
import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventType;
import org.keycloak.events.admin.AdminEvent;
import org.keycloak.events.admin.OperationType;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CustomEventListenerProvider implements EventListenerProvider {

    private static final Logger log = Logger.getLogger(CustomEventListenerProvider.class);

    private final CloseableHttpClient client = HttpClients.createDefault();
    private final Set<EventType> excludedEvents;
    private final Set<OperationType> excludedAdminOperations;
    private final List<String> serverUris;
    private final String username;
    private final String password;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public CustomEventListenerProvider(Set<EventType> excludedEvents, Set<OperationType> excludedAdminOperations, List<String> serverUris, String username, String password) {
        this.excludedEvents = excludedEvents;
        this.serverUris = serverUris;
        this.excludedAdminOperations = excludedAdminOperations;
        this.username = username;
        this.password = password;
    }

    @Override
    public void onEvent(Event event) {
        if (excludedEvents == null || !excludedEvents.contains(event.getType())) {
            String stringEvent = toString(event);
            log.info("Event to react to: " + stringEvent);
            this.sendRequest(stringEvent);
        }
    }

    @Override
    public void onEvent(AdminEvent event, boolean includeRepresentation) {
        if (excludedAdminOperations == null || !excludedAdminOperations.contains(event.getOperationType())) {
            String stringEvent = toString(event);
            log.info("Event to react to: " + stringEvent);
            this.sendRequest(stringEvent);
        }
    }

    private void sendRequest(String stringEvent) {
        serverUris.forEach(serverUri -> {
            try {
                HttpPost httpPost = new HttpPost(serverUri);
                StringEntity entity = new StringEntity(stringEvent);
                httpPost.setEntity(entity);
                httpPost.setHeader("Accept", "application/json");
                httpPost.setHeader("Content-type", "application/json");
                httpPost.setHeader("User-Agent", "KeycloakHttp Bot");

                if (this.username != null && this.password != null) {
                    UsernamePasswordCredentials creds = new UsernamePasswordCredentials(this.username, this.password);
                    httpPost.addHeader(new BasicScheme().authenticate(creds, httpPost, null));
                }

                CloseableHttpResponse response = client.execute(httpPost);

                if (response.getStatusLine().getStatusCode() != 200) {
                    throw new IOException("Unexpected code " + response);
                }
            } catch (Exception e) {
                log.error("Error while requesting the webhook " + e.getMessage());
                log.trace("Full stack trace: ", e);
            }
        });
    }

    private String toString(Event event) {
        try {
            HashMap<String, Object> resultMap = new HashMap<>();
            resultMap.put("type", event.getType());
            resultMap.put("realmId", event.getRealmId());
            resultMap.put("clientId", event.getClientId());
            resultMap.put("userId", event.getUserId());
            resultMap.put("ipAddress", event.getIpAddress());

            String eventError = event.getError();
            if (eventError != null && eventError.length() > 0) {
                resultMap.put("error", eventError);
            }
            Map<String, String> details = event.getDetails();
            if (details != null && !details.isEmpty()) {
                resultMap.put("details", details);
            }
            return objectMapper.writeValueAsString(resultMap);
        } catch (JsonProcessingException e) {
            log.error("Could not serialize JSON: " + e.getMessage());
            log.trace("Full stack trace: ", e);
            return "";
        }
    }

    private String toString(AdminEvent adminEvent) {
        try {
            HashMap<String, Object> resultMap = new HashMap<>();
            resultMap.put("type", adminEvent.getOperationType());
            resultMap.put("realmId", adminEvent.getAuthDetails().getRealmId());
            resultMap.put("clientId", adminEvent.getAuthDetails().getClientId());
            resultMap.put("userId", adminEvent.getAuthDetails().getUserId());
            resultMap.put("ipAddress", adminEvent.getAuthDetails().getIpAddress());
            resultMap.put("resourcePath", adminEvent.getResourcePath());
            resultMap.put("resourceType", adminEvent.getResourceType());
            String eventError = adminEvent.getError();
            if (eventError != null && eventError.length() > 0) {
                resultMap.put("error", eventError);
            }
            return objectMapper.writeValueAsString(resultMap);
        } catch (JsonProcessingException e) {
            log.error("Could not serialize JSON: " + e.getMessage());
            log.trace("Full stack trace: ", e);
            return "";
        }
    }

    @Override
    public void close() {
    }

}

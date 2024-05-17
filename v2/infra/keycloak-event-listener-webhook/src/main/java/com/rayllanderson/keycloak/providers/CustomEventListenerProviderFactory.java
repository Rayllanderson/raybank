package com.rayllanderson.keycloak.providers;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rayllanderson.sqs.SqsEventPublisher;
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

public class CustomEventListenerProviderFactory implements EventListenerProviderFactory {

    private final Set<Object> eventAllowlist = new HashSet<>(Arrays.asList(
            EventType.CLIENT_REGISTER,
            EventType.UPDATE_PROFILE,
            EventType.REGISTER,
            EventType.UPDATE_EMAIL,
            OperationType.CREATE,
            OperationType.UPDATE
    ));
    private static final Logger log = Logger.getLogger(CustomEventListenerProviderFactory.class);
    private SqsEventPublisher sqsEventPublisher;

    @Override
    public EventListenerProvider create(KeycloakSession session) {
        return new CustomEventListenerProvider(sqsEventPublisher, eventAllowlist);
    }

    @Override
    public void init(Config.Scope config) {
        String sqsQueue = System.getenv("KC_SQS_QUEUE");
        log.info("Configuration to publish event in dedicated sqs queue : " + sqsQueue);
        final AmazonSQS amazonSQS = createAmazonSQS();
        final ObjectMapper objectMapper = new ObjectMapper();
        sqsEventPublisher = new SqsEventPublisher(amazonSQS, sqsQueue, objectMapper);
    }

    private AmazonSQS createAmazonSQS() {
        String isSqsLocal = System.getenv("SQS_LOCAL");
        log.info(" sqs local ? : " + System.getenv("SQS_LOCAL"));
        if (Boolean.parseBoolean(isSqsLocal)) {
            String sqsUrl = System.getenv("SQS_LOCAL_URL");
            String awsRegion = System.getenv("AWS_REGION");
            log.info("Configuration to publish event in local sqs queue : " + sqsUrl + " in region : " + awsRegion);
            return AmazonSQSAsyncClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials("accessKey", "secretKey")))
                    .withEndpointConfiguration(new com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration(sqsUrl, awsRegion)).build();
        }
        return AmazonSQSAsyncClientBuilder.standard().build();
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

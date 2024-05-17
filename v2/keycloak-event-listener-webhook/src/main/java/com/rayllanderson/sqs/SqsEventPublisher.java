package com.rayllanderson.sqs;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jboss.logging.Logger;

public class SqsEventPublisher {

    private final AmazonSQS amazonSQS;
    private final String queueUrl;
    private final ObjectMapper objectMapper;
    private static final Logger log = Logger.getLogger(SqsEventPublisher.class);

    public SqsEventPublisher(AmazonSQS amazonSQS, String queueUrl, ObjectMapper objectMapper) {
        this.amazonSQS = amazonSQS;
        this.objectMapper = objectMapper;
        this.queueUrl = queueUrl;
    }

    public void publishEvent(final Object event) {
        try {
            SendMessageRequest sqsMessage = new SendMessageRequest()
                    .withQueueUrl(queueUrl)
                    .withMessageBody(objectMapper.writeValueAsString(event))
                    .withDelaySeconds(5);
            amazonSQS.sendMessage(sqsMessage);
        } catch (JsonProcessingException e) {
            log.error("The payload wasn't created.", e);
        } catch (Exception e) {
            log.error("Exception occurred during the event publication", e);
        }
    }
}

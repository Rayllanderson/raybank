package com.rayllanderson.raybank.users.consumer.thumbnail;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rayllanderson.raybank.users.services.profile.UpdateThumbnailService;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static io.awspring.cloud.sqs.annotation.SqsListenerAcknowledgementMode.ON_SUCCESS;

@Slf4j
@Component
@RequiredArgsConstructor
public class ThumbnailCreatedEventConsumer {
    private final ObjectMapper objectMapper;
    private final UpdateThumbnailService updateThumbnailService;

    @SneakyThrows
    @SqsListener(value = "${sqs.thumbnail-queue-name}", acknowledgementMode = ON_SUCCESS)
    public void listen(final String message) {
        SqsMessage sqsMessage = objectMapper.readValue(message, SqsMessage.class);

        if (sqsMessage.keyWithoutPrefix() == null) return;

        updateThumbnailService.updateThumbnail(sqsMessage);
    }
}

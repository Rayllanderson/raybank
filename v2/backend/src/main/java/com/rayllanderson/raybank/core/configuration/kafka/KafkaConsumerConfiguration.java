package com.rayllanderson.raybank.core.configuration.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;

import static org.springframework.kafka.listener.ContainerProperties.AckMode.MANUAL_IMMEDIATE;

@Slf4j
@Configuration
public class KafkaConsumerConfiguration {

    @Bean("customKafkaListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<Object, Object> kafkaListenerContainerFactory(ConsumerFactory<Object, Object> productConsumerFactory) {
        var factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(productConsumerFactory);
        factory.getContainerProperties().setAckMode(MANUAL_IMMEDIATE);
        factory.setAckDiscarded(true);
        factory.setConcurrency(2);
        return factory;
    }
}

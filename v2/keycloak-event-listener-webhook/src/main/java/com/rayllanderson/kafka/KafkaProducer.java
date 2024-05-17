package com.rayllanderson.kafka;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class KafkaProducer {

	private static final String BOOTSTRAP_SERVER = "kafka:9092";
	private static final String TOPIC = "keycloak-event-listener-topic";

	public static void publish(String value){
		resetThreadContext();

		final var producer = new org.apache.kafka.clients.producer.KafkaProducer<String, String>(getProperties());
		ProducerRecord<String, String> eventRecord = new ProducerRecord<>(TOPIC, value);
		producer.send(eventRecord);
		producer.flush();
		producer.close();
	}

	private static void resetThreadContext() {
		Thread.currentThread().setContextClassLoader(null);
	}

	public static Properties getProperties() {
		Properties properties = new Properties();
		properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER);
		properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		return properties;
	}

}

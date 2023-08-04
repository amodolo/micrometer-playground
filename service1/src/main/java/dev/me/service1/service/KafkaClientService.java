package dev.me.service1.service;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

@ApplicationScoped
@Slf4j
public class KafkaClientService {
    KafkaProducer<String, String> producer;

    @PostConstruct
    void init() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9093");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producer = new KafkaProducer<>(props);

    }

    @PreDestroy
    void shutdown() {
        producer.close();
    }

    public void send(String topic, String key, String value) {
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topic, key, value);
        producer.send(producerRecord, (metadata, exception) -> {
            if (exception == null) {
                log.info("message sent to partition:{} - offset: {} - timestamp:{}", metadata.partition(), metadata.offset(), metadata.timestamp());
            } else {
                log.warn("message has not been sent", exception);
            }
        });
    }
}

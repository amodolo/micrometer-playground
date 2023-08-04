package dev.me.service3.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class KafkaClientService {
    final KafkaConsumer<String, String> consumer;
    AtomicBoolean closed;

    public KafkaClientService() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9093");
        props.put("group.id", "service3-group");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        consumer = new KafkaConsumer<>(props);
        closed = new AtomicBoolean(false);
    }

    public void subscribe(List<String> topics) {
        log.info("subscribe(topics={})", topics);
        consumer.subscribe(topics);
        try {
            while (!closed.get()) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
                for (ConsumerRecord<String, String> rec : records) {
                    log.info("Message received from topic: {} - Key: {} - Value: {}", rec.topic(), rec.key(), rec.value());
                }
            }
        } catch (Exception e) {
            consumer.close();
        }
    }

    public void shutdown() {
        closed.set(true);
        log.info("Shutting down kafka consumer");
        consumer.wakeup();
    }

    String getKafkaServers() {
        String url = System.getenv("KAFKA_SERVERS");
        return url != null ? url : "localhost:29092";
    }
}

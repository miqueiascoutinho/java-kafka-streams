package br.com.miqueias.kafka.kafkastreams.repository.producer;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
@Slf4j
public class DuplicateProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    public void process(String topic, String key, String message) {
        log.info("..: DuplicateProducer -> topic [{}] -> key: {} -> message {}", topic, key, message);
        kafkaTemplate.send(topic, null, key, message);
    }
}

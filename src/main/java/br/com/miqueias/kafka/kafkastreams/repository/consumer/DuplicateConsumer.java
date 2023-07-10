package br.com.miqueias.kafka.kafkastreams.repository.consumer;

import br.com.miqueias.kafka.kafkastreams.repository.producer.DuplicateProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Slf4j
@RequiredArgsConstructor
@Component
public class DuplicateConsumer {

    @Value("${topic.duplicate.out}")
    private String topicOut;

    private final DuplicateProducer duplicateProducer;
    @KafkaListener(topics = "${topic.duplicate.in}", groupId = "orders-duplicate")
    public void process(ConsumerRecord<String, String> record) {
      log.info(".. DuplicateConsumer -> key: {} and value {}", record.key(), record.value());

      duplicateProducer.process(topicOut, record.key(), record.value());
    }
}

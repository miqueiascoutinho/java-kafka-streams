package br.com.miqueias.kafka.kafkastreams.repository;

import br.com.miqueias.kafka.kafkastreams.domain.Order;
import br.com.miqueias.kafka.kafkastreams.domain.OrderAggregate;
import br.com.miqueias.kafka.kafkastreams.domain.OrderCalculate;
import br.com.miqueias.kafka.kafkastreams.utils.CustomSerdes;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.WindowStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Objects;

@Component
@Slf4j
public class Streams {
    @Value("${topic.streams-in}") private String topicIn;
    @Value("${topic.streams-out}") private String topicOut;

    @Autowired
    public void processor(StreamsBuilder builder){
        KStream<String, Order> stream = builder.stream(topicIn, Consumed.with(Serdes.String(), CustomSerdes.Order()));

        stream  .peek((key, value) -> log.info("Streams read key {} end order {}", key, value))
                .filter((key, value) -> Objects.nonNull(value))
                .groupByKey()
                .windowedBy(TimeWindows.of(Duration.ofMinutes(1)))
                .aggregate(OrderCalculate::new, (key, order, total) -> total.adicionar(order.getValorTotal()), Materialized.with(Serdes.String(), CustomSerdes.OrderCalculate()))
                //.suppress(Suppressed.untilWindowCloses(Suppressed.BufferConfig.unbounded()))
                .toStream()
                .map((key, value) -> KeyValue.pair(key.key(), value))
                .peek((key, value) -> log.info("Steams aggregate with windowed -> key {} is R${} -> quantidade {}", key, value.getTotal(), value.getQuantidade()))
                .mapValues((key, value) -> OrderAggregate.builder().key(key.toString()).total(value.getTotal()).quantidade(value.getQuantidade()).isJanelaTempo(Boolean.TRUE).build())
                .to(topicOut, Produced.with(Serdes.String(), CustomSerdes.OrderAggregate()));


        stream.peek((key, value) -> log.info("Stream process with windowed -> key {} -> value {}-> ", key, value))
                .groupByKey()
                .aggregate(() -> new OrderCalculate(),
                        (key, order, total) ->
                            total.adicionar(order.getValorTotal())
                        , Materialized.with(Serdes.String(), CustomSerdes.OrderCalculate())
                        )
                .toStream()
                .peek((key, value) -> log.info("Steams aggregate without windowed -> key {} is R${}", key, value))
                .mapValues((key, value) -> OrderAggregate.builder().key(key).total(value.getTotal()).quantidade(value.getQuantidade()).isJanelaTempo(Boolean.FALSE).build())
                .to(topicOut, Produced.with(Serdes.String(), CustomSerdes.OrderAggregate()));
    }
}

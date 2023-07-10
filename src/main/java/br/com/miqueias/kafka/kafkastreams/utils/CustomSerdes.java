package br.com.miqueias.kafka.kafkastreams.utils;

import br.com.miqueias.kafka.kafkastreams.domain.Order;
import br.com.miqueias.kafka.kafkastreams.domain.OrderAggregate;
import br.com.miqueias.kafka.kafkastreams.domain.OrderCalculate;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;

import java.math.BigDecimal;

public class CustomSerdes {
    public static Serde<Order> Order() {
        JsonSerializer<Order> jsonSerializer = new JsonSerializer<>();
        JsonDeserializer<Order> jsonDeserializer = new JsonDeserializer<>(Order.class);
        return Serdes.serdeFrom(jsonSerializer, jsonDeserializer);
    }

    public static Serde<OrderAggregate> OrderAggregate(){
        JsonSerializer<OrderAggregate> serializer = new JsonSerializer<>();
        JsonDeserializer<OrderAggregate> deserializer = new JsonDeserializer<>(OrderAggregate.class);
        return Serdes.serdeFrom(serializer, deserializer);
    }

    public static Serde<OrderCalculate> OrderCalculate(){
        JsonSerializer<OrderCalculate> serializer = new JsonSerializer<>();
        JsonDeserializer<OrderCalculate> deserializer = new JsonDeserializer<>(OrderCalculate.class);
        return Serdes.serdeFrom(serializer, deserializer);
     }
    public static Serde<BigDecimal> BigDecimal(){
        JsonSerializer<BigDecimal> serializer = new JsonSerializer<>();
        JsonDeserializer<BigDecimal> deserializer = new JsonDeserializer<>(BigDecimal.class);
        return Serdes.serdeFrom(serializer, deserializer);
    }
}

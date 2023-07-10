package br.com.miqueias.kafka.kafkastreams.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.nio.charset.StandardCharsets;
import java.util.Map;


public class JsonDeserializer<T> implements Deserializer<T> {

    private Class<T> type;

    public JsonDeserializer(final Class<T> type) {
        this.type = type;
    }

    private final Gson gson = new GsonBuilder().create();

    @Override
    public void configure(Map<String, ?> props, boolean isKey) {
    }

    @Override
    public T deserialize(String topic, byte[] bytes) {
        if (bytes == null)
            return null;

        try {
            return (T) gson.fromJson(new String(bytes, StandardCharsets.UTF_8), type);
        } catch (Exception e) {
            throw new SerializationException("Error deserializing message", e);
        }
    }

    @Override
    public void close() {
        // nothing to do
    }
}

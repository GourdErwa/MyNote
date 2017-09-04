package com.ktcs.kafka.streams.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

/**
 * @author wei.Li by 2017/8/1
 */
public class Event implements Serde<Event> {

    private final ObjectMapper mapper = new ObjectMapper();

    public Event() {
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public void close() {

    }

    @Override
    public Serializer<Event> serializer() {
        return new Serializer<Event>() {

            @Override
            public void configure(Map<String, ?> configs, boolean isKey) {

            }

            @Override
            public byte[] serialize(String topic, Event data) {
                try {
                    return mapper.writeValueAsBytes(data);
                } catch (Exception e) {
                    throw new SerializationException("Error serializing JSON message", e);
                }
            }

            @Override
            public void close() {

            }
        };

    }

    @Override
    public Deserializer<Event> deserializer() {
        return new Deserializer<Event>() {
            @Override
            public void configure(Map<String, ?> configs, boolean isKey) {

            }

            @Override
            public Event deserialize(String topic, byte[] data) {
                Event result;
                try {
                    result = mapper.readValue(data, Event.class);
                } catch (Exception e) {
                    throw new SerializationException(e);
                }

                return result;
            }

            @Override
            public void close() {

            }
        };
    }
}

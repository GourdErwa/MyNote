package com.gourd.erwa.streams.examples.utils;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Collections;
import java.util.Map;


public class SpecificAvroSerde<T extends org.apache.avro.specific.SpecificRecord> implements Serde<T> {

    private final Serde<T> inner;

    /**
     * Constructor used by Kafka Streams.
     */
    public SpecificAvroSerde() {
        inner = Serdes.serdeFrom(new SpecificAvroSerializer<>(), new SpecificAvroDeserializer<>());
    }

    public SpecificAvroSerde(SchemaRegistryClient client) {
        this(client, Collections.emptyMap());
    }

    public SpecificAvroSerde(SchemaRegistryClient client, Map<String, ?> props) {
        inner = Serdes.serdeFrom(new SpecificAvroSerializer<>(client, props), new SpecificAvroDeserializer<>(client, props));
    }

    @Override
    public Serializer<T> serializer() {
        return inner.serializer();
    }

    @Override
    public Deserializer<T> deserializer() {
        return inner.deserializer();
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        inner.serializer().configure(configs, isKey);
        inner.deserializer().configure(configs, isKey);
    }

    @Override
    public void close() {
        inner.serializer().close();
        inner.deserializer().close();
    }

}

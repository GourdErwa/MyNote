package com.gourd.erwa.streams.examples.utils;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Comparator;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * Note: This serde is not functional yet because `PriorityQueueSerializer` and
 * `PriorityQueueDeserializer` are not functional in turn.
 */
public class PriorityQueueSerde<T> implements Serde<PriorityQueue<T>> {

    private final Serde<PriorityQueue<T>> inner;

    /**
     * Constructor used by Kafka Streams.
     *
     * @param comparator
     * @param avroSerde
     */
    public PriorityQueueSerde(final Comparator<T> comparator, final Serde<T> valueSerde) {
        inner = Serdes.serdeFrom(new PriorityQueueSerializer<>(comparator, valueSerde.serializer()),
                new PriorityQueueDeserializer<>(comparator, valueSerde.deserializer()));
    }

    @Override
    public Serializer<PriorityQueue<T>> serializer() {
        return inner.serializer();
    }

    @Override
    public Deserializer<PriorityQueue<T>> deserializer() {
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

package com.ktcs.kafka.Interceptor;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author wei.Li by 2017/7/28
 */
public class JSONProducerInterceptor implements ProducerInterceptor {

    //public static final String FILE_PATH = "/data/kafka/logs/JSONProducerInterceptor.log";
    private static final Logger LOGGER = LoggerFactory.getLogger(JSONProducerInterceptor.class);

    @Override
    public ProducerRecord onSend(ProducerRecord record) {

        LOGGER.error("record {}", record);


        return record;
    }

    @Override
    public void onAcknowledgement(RecordMetadata metadata, Exception exception) {

    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> configs) {

    }


}

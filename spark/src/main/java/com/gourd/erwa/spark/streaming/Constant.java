package com.gourd.erwa.spark.streaming;

import java.util.regex.Pattern;

/**
 * @author wei.Li by 2017/9/20
 */
interface Constant {

    //String BROKERS = "172.17.12.3:9092,172.17.12.2:9092,172.17.12.4:9092";
    String BROKERS = "172.17.12.3:9092,172.17.12.2:9092,172.17.12.4:9092";
    String TOPICS = "log_original_sbkcq_test";
    String GROUP = "spark_streaming_kafka_test";
    String SPARK_CONF_MASTER = "spark://h7:7077";

    Pattern COMPILE_TOPICS = Pattern.compile("log_original_.*");

}

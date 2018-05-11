package com.gourd.erwa.spark.streaming;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;
import org.elasticsearch.spark.rdd.api.java.JavaEsSpark;

import java.util.HashMap;
import java.util.Map;

/**
 * Consumes messages from one or more topics in Kafka and does wordcount.
 * Usage: JavaDirectKafkaWordCount <brokers> <topics>
 * <brokers> is a list of one or more Kafka brokers
 * <topics> is a list of one or more kafka topics to consume from
 * <p>
 * Example:
 * $ bin/run-example streaming.JavaDirectKafkaWordCount broker1-host:port,broker2-host:port \
 * topic1,topic2
 */
public class JavaDirectKafkaWordCount {

    public static void main(String[] args) throws Exception {


        // Create javaStreamingContext with a 2 seconds batch interval
        final SparkConf sc = new SparkConf()
                .setAppName("JavaDirectKafkaWordCount-toES")
                //.set("spark.driver.allowMultipleContexts", "true")
                .set("es.nodes", "master:9200,slave1:9200,slave2:9200")
                .set("es.index.auto.create", "true")
                .set("es.input.json", "true");

        final JavaSparkContext javaSparkContext = JavaSparkContext.fromSparkContext(SparkContext.getOrCreate(sc));

        final JavaStreamingContext javaStreamingContext = new JavaStreamingContext(javaSparkContext, Durations.seconds(30));

        final Map<String, Object> kafkaParams = new HashMap<>();
        kafkaParams.put("bootstrap.servers", Constant.BROKERS);
        kafkaParams.put("group.id", Constant.GROUP);
        kafkaParams.put("key.deserializer", StringDeserializer.class);
        kafkaParams.put("value.deserializer", StringDeserializer.class);
        kafkaParams.put("auto.offset.reset", "latest");
        kafkaParams.put("enable.auto.commit", false);


        // Create direct kafka stream with brokers and topics
        final JavaInputDStream<ConsumerRecord<String, String>> messages = KafkaUtils.createDirectStream(
                javaStreamingContext,
                LocationStrategies.PreferConsistent(),
                ConsumerStrategies.SubscribePattern(Constant.COMPILE_TOPICS, kafkaParams)
        );

        // Get the lines, split them into words, count the words and print
        final JavaDStream<String> lines = messages.map(record -> {

            final JSONObject jsonObject = JSON.parseObject(record.value());
            //jsonObject.
            return record.value();
        });
        lines.print();

        lines.foreachRDD(
                (VoidFunction<JavaRDD<String>>) stringJavaRDD ->
                        //stringJavaRDD.id()
                        JavaEsSpark.saveJsonToEs(stringJavaRDD, "bi_spark_{param_data.game_key_s}-{@timestamp:YYYY.MM.dd}/{param_data.category_s}")
        );


        // Start the computation
        javaStreamingContext.start();
        javaStreamingContext.awaitTermination();
    }
}

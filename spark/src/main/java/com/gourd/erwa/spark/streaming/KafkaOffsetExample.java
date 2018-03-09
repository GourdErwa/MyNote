package com.gourd.erwa.spark.streaming;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;
import scala.Tuple2;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * bin/spark-submit --class com.gourd.erwa.spark.streaming.KafkaOffsetExample  /data/spark/tmp/jars/spark_kafka_test.jar
 *
 * @author wei.Li by 2017/9/20
 */
public class KafkaOffsetExample {


    public static void main(String[] args) throws InterruptedException {

        Map<String, Object> kafkaParams = new HashMap<>();
        kafkaParams.put("bootstrap.servers", Constant.BROKERS);
        kafkaParams.put("key.deserializer", StringDeserializer.class);
        kafkaParams.put("value.deserializer", StringDeserializer.class);
        kafkaParams.put("group.id", Constant.GROUP);
        kafkaParams.put("auto.offset.reset", "latest");
        kafkaParams.put("enable.auto.commit", false);

        Collection<String> topics = Collections.singletonList(Constant.TOPICS);


        SparkConf conf = new SparkConf().setMaster(Constant.SPARK_CONF_MASTER).setAppName("KafkaOffsetExample");
        JavaSparkContext sc = new JavaSparkContext(conf);
        sc.setLogLevel("DEBUG");
        JavaStreamingContext ssc = new JavaStreamingContext(sc, Durations.seconds(5));

        JavaInputDStream<ConsumerRecord<String, String>> stream =
                KafkaUtils.createDirectStream(
                        ssc,
                        LocationStrategies.PreferConsistent(),
                        ConsumerStrategies.<String, String>Subscribe(topics, kafkaParams)
                );

        stream.mapToPair(o -> {
            System.out.println(
                    o.topic() + " " + o.partition()
            );
            return new Tuple2<>(o.key(), o.value());
        });


/*
        stream.foreachRDD(rdd -> {
            OffsetRange[] offsetRanges = ((HasOffsetRanges) rdd.rdd()).offsetRanges();
            rdd.foreachPartition(consumerRecords -> {
                OffsetRange o = offsetRanges[TaskContext.get().partitionId()];
                System.out.println(
                        o.topic() + " " + o.partition() + " " + o.fromOffset() + " " + o.untilOffset()
                );
            });
        });
*/


        // Import dependencies and create kafka params as in Create Direct Stream above

        /*OffsetRange[] offsetRanges = {
                // topic, partition, inclusive starting offset, exclusive ending offset
                OffsetRange.create("test", 0, 0, 100),
                OffsetRange.create("test", 1, 0, 100)
        };*/

        /*JavaRDD<ConsumerRecord<String, String>> rdd = KafkaUtils.createRDD(
                sparkContext,
                kafkaParams,
                offsetRanges,
                LocationStrategies.PreferConsistent()
        );*/


        /*stream.foreachRDD(rdd -> {
            OffsetRange[] offsetRanges = ((HasOffsetRanges) rdd.rdd()).offsetRanges();

            // some time later, after outputs have completed
            ((CanCommitOffsets) stream.inputDStream()).commitAsync(offsetRanges);
        });*/

        ssc.start();
        ssc.awaitTermination();
    }
}

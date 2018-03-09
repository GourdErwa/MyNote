package com.gourd.erwa.spark.streaming;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;
import scala.Tuple2;

import java.util.*;

/**
 * @author wei.Li by 2017/9/19
 */
public class SparkSteamingKafka {

    public static void main(String[] args) throws InterruptedException {
        String brokers = "172.17.12.3:9092,172.17.12.2:9092,172.17.12.4:9092";
        String topics = "log_original_sbkcq_test";
        String group = "spark_streaming_kafka_test";


        SparkConf conf = new SparkConf().setMaster("spark://h7:7077").setAppName("SparkSteamingKafka");

        JavaSparkContext sc = new JavaSparkContext(conf);
        sc.setLogLevel("WARN");
        JavaStreamingContext ssc = new JavaStreamingContext(sc, Durations.seconds(5));

        Collection<String> topicsSet = new HashSet<>(Arrays.asList(topics.split(",")));
        //kafka相关参数，必要！缺了会报错
        Map<String, Object> kafkaParams = new HashMap<>();
        kafkaParams.put("bootstrap.servers", brokers);
        kafkaParams.put("group.id", group);
        kafkaParams.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        kafkaParams.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        kafkaParams.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        kafkaParams.put("auto.offset.reset", "latest");

        //Topic分区
        Map<TopicPartition, Long> offsets = new HashMap<>();
        offsets.put(new TopicPartition(topics, 0), 100338L);
        //通过KafkaUtils.createDirectStream(...)获得kafka数据，kafka相关参数由kafkaParams指定
        JavaInputDStream<ConsumerRecord<Object, Object>> lines = KafkaUtils.createDirectStream(
                ssc,
                LocationStrategies.PreferConsistent(),
                ConsumerStrategies.Subscribe(topicsSet, kafkaParams)
        );
        //这里就跟之前的demo一样了，只是需要注意这边的lines里的参数本身是个ConsumerRecord对象
        JavaPairDStream<String, Integer> counts =
                lines.flatMap(x -> {
                    System.out.println(x);
                    return Arrays.asList(x.value().toString().split(" ")).iterator();
                })
                        .mapToPair(x -> new Tuple2<>(x, 1))
                        .reduceByKey((x, y) -> x + y);

        counts.print();
        //可以打印所有信息，看下ConsumerRecord的结构
        lines.foreachRDD(rdd -> rdd.foreach(System.out::println));

        ssc.start();
        ssc.awaitTermination();
    }
}

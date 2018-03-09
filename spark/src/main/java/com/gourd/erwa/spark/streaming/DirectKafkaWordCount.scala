package com.gourd.erwa.spark.streaming


import org.apache.kafka.common.TopicPartition
import org.apache.spark.{SparkConf, SparkContext, TaskContext}
import org.apache.spark.streaming.{Durations, Seconds, StreamingContext}
import org.apache.spark.streaming.kafka010._

import org.apache.kafka.common.serialization.StringDeserializer

/**
  *
  * Consumes messages from one or more topics in Kafka and does wordcount.
  * Usage: DirectKafkaWordCount <brokers> <topics>
  * <brokers> is a list of one or more Kafka brokers
  * <topics> is a list of one or more kafka topics to consume from
  *
  * Example:
  * $ bin/run-example streaming.DirectKafkaWordCount broker1-host:port,broker2-host:port \
  * topic1,topic2
  *
  * @author wei.Li by 2017/9/19
  */
object DirectKafkaWordCount {
  def main(args: Array[String]) {

    /*
    bin/spark-submit \
	--master spark://h7:7077 \
	--class com.gourd.erwa.spark.streaming.DirectKafkaWordCount \
	/data/spark/tmp/jars/spark_kafka_test.jar 172.17.12.3:9092,172.17.12.2:9092,172.17.12.4:9092 log_original_sbkcq_test

     */
    val brokers = "172.17.12.3:9092,172.17.12.2:9092,172.17.12.4:9092"
    val sc = SparkContext.getOrCreate
    val ssc = new StreamingContext(sc, Seconds(10))

    val preferredHosts = LocationStrategies.PreferConsistent
    val topics = List("log_original_sbkcq_test")
    val kafkaParams = Map(
      "bootstrap.servers" -> brokers,
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "group.id" -> "spark-streaming-notes",
      "auto.offset.reset" -> "latest"
    )
    val offsets: Map[TopicPartition, Long] = Map()

    for (i <- 0 until 3) {
      val tp = new TopicPartition("log_original_sbkcq_test", i)
      offsets.updated(tp, 0L)
    }

    val stream = KafkaUtils.createDirectStream[String, String](
      ssc,
      preferredHosts,
      ConsumerStrategies.Subscribe[String, String](topics, kafkaParams))

    println("directStream")
    stream.foreachRDD { rdd =>
      //输出获得的消息
      rdd.foreach { iter =>
        val i = iter.value
        println(s"$i")
      }
      //获得offset
      val offsetRanges = rdd.asInstanceOf[HasOffsetRanges].offsetRanges
      rdd.foreachPartition { iter =>
        val o: OffsetRange = offsetRanges(TaskContext.get.partitionId)
        println(s"${o.topic} ${o.partition} ${o.fromOffset} ${o.untilOffset}")
      }
    }

    // Start the computation
    ssc.start()
    ssc.awaitTermination()
  }
}


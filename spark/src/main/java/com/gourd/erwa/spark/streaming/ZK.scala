/*
package com.gourd.erwa.spark.streaming

import kafka.utils.{ZKGroupTopicDirs, ZkUtils}
import org.apache.kafka.common.TopicPartition
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, KafkaUtils, OffsetRange}
import org.apache.zookeeper.data.ACL

import scala.collection.JavaConversions
import scala.collection.mutable.ListBuffer

/**
  * https://blog.cloudera.com/blog/2017/06/offset-management-for-apache-kafka-with-apache-spark-streaming/
  *
  * @author wei.Li by 2017/9/19
  */
class ZK {

  /**
    * Initialize ZooKeeper connection for retrieving and storing offsets to ZooKeeper.
    */
  val zkClientAndConnection = ZkUtils.createZkClientAndConnection(zkUrl, sessionTimeout, connectionTimeout)
  val zkUtils = new ZkUtils(zkClientAndConnection._1, zkClientAndConnection._2, false)

  /**
    * Method for retrieving the last offsets stored in ZooKeeper of the consumer group and topic list.
    *
    * @param topics topics
    * @param groupId groupId
    */
  def readOffsets(topics: Seq[String], groupId: String):
  Map[TopicPartition, Long] = {

    val topicPartOffsetMap = collection.mutable.HashMap.empty[TopicPartition, Long]
    val partitionMap = zkUtils.getPartitionsForTopics(topics)

    // /consumers/<groupId>/offsets/<topic>/
    partitionMap.foreach(topicPartitions => {
      val zkGroupTopicDirs = new ZKGroupTopicDirs(groupId, topicPartitions._1)
      topicPartitions._2.foreach(partition => {
        val offsetPath = zkGroupTopicDirs.consumerOffsetDir + "/" + partition

        try {
          val offsetStatTuple = zkUtils.readData(offsetPath)
          if (offsetStatTuple != null) {
            LOGGER.info("retrieving offset details - topic: {}, partition: {}, offset: {}, node path: {}", Seq[AnyRef](topicPartitions._1, partition.toString, offsetStatTuple._1, offsetPath): _*)

            topicPartOffsetMap.put(new TopicPartition(topicPartitions._1, Integer.valueOf(partition)),
              offsetStatTuple._1.toLong)
          }

        } catch {
          case e: Exception =>
            LOGGER.warn("retrieving offset details - no previous node exists:" + " {}, topic: {}, partition: {}, node path: {}", Seq[AnyRef](e.getMessage, topicPartitions._1, partition.toString, offsetPath): _*)

            topicPartOffsetMap.put(new TopicPartition(topicPartitions._1, Integer.valueOf(partition)), 0L)
        }
      })
    })

    topicPartOffsetMap.toMap
  }

  /**
    * Initialization of Kafka Direct Dstream with the specific offsets to start processing from.
    *
    */
  val inputDStream = KafkaUtils.createDirectStream(ssc, PreferConsistent, ConsumerStrategies.Subscribe[String, String](topics, kafkaParams, fromOffsets))


  /**
    * Method for persisting a recoverable set of offsets to ZooKeeper.
    * *
    * Note: The offsetPath is a ZooKeeper location represented as, /consumers/[groupId]/offsets/topic/[partitionId], that stores the value of the offset.
    *
    * @param offsets offsets
    * @param groupId groupId
    * @param storeEndOffset storeEndOffset
    */
  def persistOffsets(offsets: Seq[OffsetRange], groupId: String, storeEndOffset: Boolean): Unit = {
    offsets.foreach(or => {
      val zkGroupTopicDirs = new ZKGroupTopicDirs(groupId, or.topic);

      val acls = new ListBuffer[ACL]()
      val acl = new ACL
      acl.setId(ANYONE_ID_UNSAFE)
      acl.setPerms(PERMISSIONS_ALL)
      acls += acl

      val offsetPath = zkGroupTopicDirs.consumerOffsetDir + "/" + or.partition;
      val offsetVal = if (storeEndOffset) or.untilOffset else or.fromOffset
      zkUtils.updatePersistentPath(zkGroupTopicDirs.consumerOffsetDir + "/"
        + or.partition, offsetVal + "", JavaConversions.bufferAsJavaList(acls))

      LOGGER.debug(
        "persisting offset details - topic: {}, partition: {}, offset: {}, node path: {}",
        Seq[AnyRef](or.topic, or.partition.toString, offsetVal.toString, offsetPath): _*
      )
    })
  }
}
*/

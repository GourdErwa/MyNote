package com.gourd.erwa;


import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.serializer.StringDecoder;
import kafka.utils.VerifiableProperties;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author wei.Li
 */
public class KafkaConsumerTest {

    private final ConsumerConnector consumer;

    private KafkaConsumerTest() {
        Properties props = new Properties();
        //zookeeper 配置
        props.put("zookeeper.connect", "192.168.11.73:2181");

        //group 代表一个消费组,加入组里面,消息只能被该组的一个消费者消费
        //如果所有消费者在一个组内,就是传统的队列模式,排队拿消息
        //如果所有的消费者都不在同一个组内,就是发布-订阅模式,消息广播给所有组
        //如果介于两者之间,那么广播的消息在组内也是要排队的
        props.put("group.id", "jd-group");

        //zk连接超时
        props.put("zookeeper.session.timeout.ms", "4000");//ZooKeeper的最大超时时间，就是心跳的间隔，若是没有反映，那么认为已经死了，不易过大
        props.put("zookeeper.sync.time.ms", "200");//zk follower落后于zk leader的最长时间
        props.put("auto.commit.interval.ms", "1000");//往zookeeper上写offset的频率
        /*
        * 此配置参数表示当此groupId下的消费者,在ZK中没有offset值时(比如新的groupId,或者是zk数据被清空),consumer应该从哪个offset开始消费.
        * largest表示接受接收最大的offset(即最新消息),smallest表示最小offset,即从topic的开始位置消费所有消息.
        * */
        props.put("auto.offset.reset", "smallest");  //消费最老消息,最新为largest
        //序列化类
        props.put("serializer.class", "kafka.serializer.StringEncoder");

        ConsumerConfig config = new ConsumerConfig(props);

        consumer = Consumer.createJavaConsumerConnector(config);
    }

    public static void main(String[] args) {
        new KafkaConsumerTest().consume();
    }

    private void consume() {
        // 描述读取哪个topic，需要几个线程读
        Map<String, Integer> topicCountMap = new HashMap<>();
        topicCountMap.put(KafkaProduceTest.TOPIC, 1);


        /* 默认消费时的数据是byte[]形式的,可以传入String编码器*/
        StringDecoder keyDecoder = new StringDecoder(new VerifiableProperties());
        StringDecoder valueDecoder = new StringDecoder(new VerifiableProperties());

        Map<String, List<KafkaStream<String, String>>> consumerMap =
                consumer.createMessageStreams(topicCountMap, keyDecoder, valueDecoder);

        //消费数据时每个Topic有多个线程在读,所以取List第一个流
        KafkaStream<String, String> stream = consumerMap.get(KafkaProduceTest.TOPIC).get(0);
        ConsumerIterator<String, String> it = stream.iterator();
        while (it.hasNext())
            System.out.println(it.next().topic() + ":" + it.next().partition() + ":" + it.next()
                    .offset() + ":" + it.next().key() + ":" + it.next().message());
    }
}

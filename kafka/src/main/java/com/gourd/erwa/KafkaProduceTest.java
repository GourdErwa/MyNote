package com.gourd.erwa;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.util.Properties;

/**
 * @author wei.Li
 */
public class KafkaProduceTest {

    final static String TOPIC = "clicki_info_topic";
    private final Producer<String, String> producer;

    private KafkaProduceTest() {
        Properties props = new Properties();
        //此处配置的是kafka的端口
        props.put("metadata.broker.list", "192.168.11.73:9092");

        //配置value的序列化类
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        //配置key的序列化类
        props.put("key.serializer.class", "kafka.serializer.StringEncoder");

        //0表示不确认主服务器是否收到消息,马上返回,低延迟但最弱的持久性,数据可能会丢失
        //1表示确认主服务器收到消息后才返回,持久性稍强,可是如果主服务器死掉,从服务器数据尚未同步,数据可能会丢失
        //-1表示确认所有服务器都收到数据,完美!
        props.put("request.required.acks", "-1");

        //异步生产,批量存入缓存后再发到服务器去
        props.put("producer.type", "async");

        //填充配置,初始化生产者
        producer = new Producer<>(new ProducerConfig(props));
    }

    public static void main(String[] args) {
        new KafkaProduceTest().produce();

    }

    private void produce() {
        int messageNo = 1000;
        final int COUNT = 2000;

        while (messageNo < COUNT) {
            String key = String.valueOf(messageNo);
            String data = "hello kafka message " + key;
            String data1 = "{\"c\":0,\"i\":16114765323924126,\"n\":\"http://www.abbo.cn/clicki.html\",\"s\":0,\"sid\":0,\"t\":\"info_url\",\"tid\":0,\"unix\":0,\"viewId\":0}";
            // 发送消息
//            producer.send(new KeyedMessage<String, String>(TOPIC,data1));
            // 消息类型key:value
            producer.send(new KeyedMessage<>(TOPIC, key, data));
            System.out.println(data);
            messageNo++;
        }
        producer.close();//必须关闭
    }
}

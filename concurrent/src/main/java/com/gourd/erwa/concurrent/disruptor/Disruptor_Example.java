package com.gourd.erwa.concurrent.disruptor;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.TimeoutBlockingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 生产者 Disruptor - 核心内容
 *
 * @author lw by 14-7-2.
 */
public class Disruptor_Example {

    private static final int RINGBUFFER_SIZE = 16;//这个参数应该是2的幂，否则程序会抛出异常：
    private static final ExecutorService SERVICE//线程池
            = Executors.newCachedThreadPool();
    private static RingBuffer<ValueEvent> ringBuffer;//定义环形数组内存
    private static Disruptor<ValueEvent> disruptor;

    /**
     * 创建 Disruptor 对象。
     * Disruptor 类是 Disruptor 项目的核心类，另一个核心类之一是 RingBuffer。
     * 如果把 Disruptor 比作计算机的 cpu ，作为调度中心的话，那么 RingBuffer ，就是计算机的 Memory 。
     * 第一个参数，是一个 EventFactory 对象，它负责创建 ValueEvent 对象，并填充到 RingBuffer 中；
     * 第二个参数，指定 RingBuffer 的大小。这个参数应该是2的幂，否则程序会抛出异常：
     * 第三个参数，就是之前创建的 ExecutorService 对象。
     */
    private static void init() {
        disruptor = new Disruptor<ValueEvent>(
                ValueEvent.EVENT_FACTORY,
                RINGBUFFER_SIZE,
                SERVICE,
                ProducerType.MULTI,
                new TimeoutBlockingWaitStrategy(1000, TimeUnit.MINUTES)
        );
    }

    /**
     * 添加消费者对象
     * {@link DeliveryReportEventHandler}
     *
     * @param eventHandlers 消费者对象
     */
    private static void handleEventsWith(EventHandler[] eventHandlers) {
        disruptor.handleEventsWith(eventHandlers);
    }

    /**
     * 启动disruptor
     */
    private static void start() {
        ringBuffer = disruptor.start();
    }

    /**
     * 生产者线程
     * 通过 next 方法，获取 RingBuffer 可写入的消息索引号 seq；
     * 通过 seq 检索消息；
     * 修改消息的 value 属性；
     * 通过 publish 方法，告知消费者线程，当前索引位置的消息可被消费了
     *
     * @param event 事件
     */
    private static void addEVent(ValueEvent event) {

        if (hasCapacity()) {
            System.out.println("disruptor:ringbuffer 剩余量低于 10 %");
        } else {
            long seq = ringBuffer.next();
            /**
             * @see ValueEvent.< DeliveryReportEventHandler >
             */
            ValueEvent valueEvent = ringBuffer.get(seq);//获取可用位置
            valueEvent.setValue(event.getValue());//填充可用位置
            ringBuffer.publish(seq);//通知消费者
        }
    }

    /**
     * 停止 Disruptor系统（停止消费者线程）
     */
    private static void shutdown() {
        disruptor.shutdown();
        SERVICE.shutdown();
    }

    /**
     * 获取ringBuffer剩余量是否低于RINGBUFFER_SIZE * 0.1
     *
     * @return boolean
     */
    private static boolean hasCapacity() {
        return (ringBuffer.remainingCapacity() < RINGBUFFER_SIZE * 0.1);
    }

    public static void main(String[] args) {

        init();//初始化
        handleEventsWith(new EventHandler[]{new DeliveryReportEventHandler(1), new DeliveryReportEventHandler(2)});//添加2个消费者
        start();//启动disruptor

        //生产10个商品
        for (int i = 0; i < 10; i++) {
            ValueEvent valueEvent = new ValueEvent();
            valueEvent.setValue(UUID.randomUUID().toString());
            addEVent(valueEvent);
        }
        //停止
        shutdown();
    }

}

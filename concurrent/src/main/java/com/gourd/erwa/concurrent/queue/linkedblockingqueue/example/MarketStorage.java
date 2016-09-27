package com.gourd.erwa.concurrent.queue.linkedblockingqueue.example;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 市场演示仓库
 *
 * @author wei.Li by 14-8-21.
 */
public class MarketStorage {

    //生产者线程睡眠随机最大时间
    static final int PRODUCER_THREAD_SLEEP = 200;
    //消费者线程睡眠随机最大时间
    static final int CONSUMER_THREAD_SLEEP = 1000;
    //生产者线程池
    private static final ExecutorService EXECUTOR_SERVICE_PRODUCER
            = Executors.newFixedThreadPool(10);
    //启动生产者线程数量
    private static final int PRODUCER_THREAD_NUM = 2;
    //消费者线程池
    private static final ExecutorService EXECUTOR_SERVICE_CONSUMER
            = Executors.newFixedThreadPool(10);
    //启动消费者线程数量
    private static final int CONSUMER_THREAD_NUM = 20;
    //生产者生成对象次数
    static AtomicInteger getProducerObj_Count = new AtomicInteger(0);
    //消费者消费对象次数
    static AtomicInteger getConsumerObj_Count = new AtomicInteger(0);
    //是否消费
    static boolean isRun_Cousumer = true;

    //市场仓库-存储数据的队列 默认仓库容量大小100
    /**
     * @see thread.concurrent.queue.linkedblockingqueue.LinkedBlockingQueue_#linkedBlockingQueue2Void()
     */
    static LinkedBlockingQueue<CommodityObj> blockingQueue
            = new LinkedBlockingQueue<CommodityObj>(100);

    /**
     * 生成生产者线程
     */
    private static void runProducer() {
        for (int i = 0; i < PRODUCER_THREAD_NUM; i++) {
            EXECUTOR_SERVICE_PRODUCER.submit(new Producer());
        }
    }

    /**
     * 生成消费者线程生成
     */
    private static void runConsumer() {
        for (int i = 0; i < CONSUMER_THREAD_NUM; i++) {
            Thread thread = new Thread(new Consumer());
            EXECUTOR_SERVICE_CONSUMER.submit(thread);
        }
    }

    /**
     * 停止线程 生产与消费
     * 关闭线程池
     */
    private static void shumdown() {
        if (!EXECUTOR_SERVICE_PRODUCER.isShutdown()) {
            EXECUTOR_SERVICE_PRODUCER.shutdown();
        }
        if (!EXECUTOR_SERVICE_CONSUMER.isShutdown()) {
            isRun_Cousumer = false;
            EXECUTOR_SERVICE_CONSUMER.shutdown();
        }
    }


    public static void main(String[] args) {
        runConsumer();
        runProducer();

        /*
         * 10 s 后停止执行
         */
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                shumdown();
                System.out.println("~~~~~~~~~~~~ shumdown done ~~~~~~~~~~~~~~");
            }
        }, 1000 * 10L);
    }
}

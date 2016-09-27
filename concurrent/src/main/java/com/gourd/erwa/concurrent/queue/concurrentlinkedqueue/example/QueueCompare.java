package com.gourd.erwa.concurrent.queue.concurrentlinkedqueue.example;


import com.gourd.erwa.util.date.DateFormatConstant;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;


/**
 * @author wei.Li by 14-8-28.
 */
public class QueueCompare {

    //测试生产数量
    private static final int PRODUCER_OBJ_NUM = 10000000;
    private static final org.slf4j.Logger LOGGER
            = LoggerFactory.getLogger(QueueCompare.class);
    //执行的线程数量
    private static final int SYNCHRONIZED_DONE_THREAD_NUM = 4;
    //线程池
    private static final ExecutorService EXECUTOR_SERVICE
            = Executors.newFixedThreadPool(SYNCHRONIZED_DONE_THREAD_NUM);
    //linkedBlockingQueue init
    private static final LinkedBlockingQueue linkedBlockingQueue
            = new LinkedBlockingQueue();
    //concurrentLinkedQueue init
    private static final ConcurrentLinkedQueue concurrentLinkedQueue
            = new ConcurrentLinkedQueue();

    private static void runTest() {

        /*
          添加concurrentLinkedQueue生产线程
         */
        Market<String> concurrentLinkedQueueMarket =
                new ConcurrentLinkedQueueMarket<>();

        EXECUTOR_SERVICE.execute(
                new ProducerHandle<>(concurrentLinkedQueueMarket, "concurrentLinkedQueueMarket")
        );
        EXECUTOR_SERVICE.execute(
                new ConsumerHandle<>(concurrentLinkedQueueMarket, "concurrentLinkedQueueMarket")
        );

        Set<Object> set
                = new HashSet<>();
        /*
         * 添加blockingQueue生产线程
         */
        Market<String> blockingQueueMarket
                = new LinkedBlockingQueueMarket<>();
        EXECUTOR_SERVICE.execute(
                new ProducerHandle<>(blockingQueueMarket, "blockingQueueMarket")
        );
        EXECUTOR_SERVICE.execute(
                new ConsumerHandle<>(blockingQueueMarket, "blockingQueueMarket")
        );


        EXECUTOR_SERVICE.shutdown();
    }

    public static void main(String[] args) {
        runTest();
    }

    /**
     * 生产者、消费者
     */
    interface Market<V> {

        void producer(V v);

        void consumer();

    }

    /**
     * concurrentLinkedQueue 的生产与消费实现
     */
    private static class ConcurrentLinkedQueueMarket<V> implements Market<V> {

        @Override
        public void producer(V o) {
            concurrentLinkedQueue.add(o);
            // LOGGER.info("concurrentLinkedQueue <{}> producer <{}>", concurrentLinkedQueue, o);
        }


        @Override
        public void consumer() {
            while (!concurrentLinkedQueue.isEmpty()) {//return first() == null; !!! size 方法是遍历队列返回总数
                concurrentLinkedQueue.poll();
                // LOGGER.info("concurrentLinkedQueue <{}> consumer <{}>", linkedBlockingQueue, o);
            }
        }
    }

    /**
     * linkedBlockingQueue 的生产与消费实现
     */
    private static class LinkedBlockingQueueMarket<V> implements Market<V> {

        @Override
        public void producer(V o) {
            try {
                linkedBlockingQueue.put(o);
                //LOGGER.info("linkedBlockingQueue <{}> producer <{}>", linkedBlockingQueue, o);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void consumer() {
            while (!linkedBlockingQueue.isEmpty()) {//return size() == 0; 与直接使用 size 方法无区别
                try {
                    linkedBlockingQueue.take();
                    // LOGGER.info("linkedBlockingQueue <{}> consumer <{}>", linkedBlockingQueue, o);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 生产处理线程
     *
     * @param <T> extends Market
     */
    private static class ProducerHandle<T extends Market<V>, V> implements Runnable {

        final T market;
        final V v;

        private ProducerHandle(T market, V v) {
            this.market = market;
            this.v = v;
        }

        @Override
        public void run() {

            for (int i = 0; i < PRODUCER_OBJ_NUM; i++) {

                market.producer(v);
            }
        }
    }

    /**
     * 消费处理线程
     *
     * @param <T> extends Market
     */
    private static class ConsumerHandle<T extends Market<V>, V> implements Runnable {

        final T market;
        final V v;

        private ConsumerHandle(T market, V v) {
            this.market = market;
            this.v = v;
        }


        @Override
        public void run() {
            market.consumer();
            LOGGER.info(" <{}> done <{}> need time <{}>"
                    , market.getClass().getSimpleName()
                    , PRODUCER_OBJ_NUM
                    , DateFormatConstant.DATE_TIME_FORMAT.format(new Date()));
        }
    }


}

/**
 * ConcurrentLinkedQueueMarket采用 size() 方法判断大小(每次遍历队列返回总数)
 *
 * @see com.gourd.erwa.concurrent.queue.concurrentlinkedqueue.example.QueueCompare.ConcurrentLinkedQueueMarket#consumer()
 * --------------------------------------------------------------------------
 * <p>
 * <LinkedBlockingQueueMarket>    done <10000> need time <2014-08-29T09:46:21>
 * <ConcurrentLinkedQueueMarket>  done <10000> need time <2014-08-29T09:46:21>
 * <p>
 * <LinkedBlockingQueueMarket>    done <100000> need time <2014-08-29T09:46:46>
 * <ConcurrentLinkedQueueMarket>  done <100000> need time <2014-08-29T09:47:12>
 * <p>
 * <LinkedBlockingQueueMarket>    done <1000000> need time <2014-08-29T09:47:33>
 * <ConcurrentLinkedQueueMarket>  done <1000000> need time <2014-08-29T.超长耗时>
 * <p>
 * <p>
 * ConcurrentLinkedQueueMarket采用 isEmpty() 方法判断大小
 * --------------------------------------------------------------------------
 * <LinkedBlockingQueueMarket>    done <1000000> need time <2014-08-29T09:53:24>
 * <ConcurrentLinkedQueueMarket>  done <1000000> need time <2014-08-29T09:53:24>
 * <p>
 * <ConcurrentLinkedQueueMarket>  done <10000000> need time <2014-08-29T09:55:54>
 * <LinkedBlockingQueueMarket>    done <10000000> need time <2014-08-29T09:55:54>
 */

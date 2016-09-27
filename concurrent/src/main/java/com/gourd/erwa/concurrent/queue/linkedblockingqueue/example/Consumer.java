package com.gourd.erwa.concurrent.queue.linkedblockingqueue.example;

import java.util.Random;

/**
 * 消费者
 *
 * @author wei.Li by 14-8-21.
 */
class Consumer implements Runnable {

    @Override
    public void run() {
        while (MarketStorage.isRun_Cousumer) {

            try {
                //随机睡眠
                Thread.sleep(new Random().nextInt(MarketStorage.CONSUMER_THREAD_SLEEP));

                //消费对象
                CommodityObj commodityObj = MarketStorage.blockingQueue.take();
                System.out.println(this + " consumer obj ->" + commodityObj);

                MarketStorage.getConsumerObj_Count.getAndIncrement();//计数器++
                System.out.println("getConsumerObj_Count is :" + MarketStorage.getConsumerObj_Count);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

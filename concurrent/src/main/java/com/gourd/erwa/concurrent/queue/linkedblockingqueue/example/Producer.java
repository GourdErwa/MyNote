package com.gourd.erwa.concurrent.queue.linkedblockingqueue.example;

import java.util.Random;

/**
 * 生产者
 *
 * @author wei.Li by 14-8-21.
 */
class Producer implements Runnable {

    @Override
    public void run() {
        while (MarketStorage.isRun_Cousumer) {
            //随机睡眠
            try {
                Thread.sleep(new Random().nextInt(MarketStorage.PRODUCER_THREAD_SLEEP));

                //生产对象
                CommodityObj commodityObj = new CommodityObj();
                MarketStorage.blockingQueue.put(commodityObj);
                System.out.println(this + " producer obj succeed->" + commodityObj);

                MarketStorage.getProducerObj_Count.getAndIncrement();//计数器++
                System.out.println("getProducerObj_Count is :" + MarketStorage.getProducerObj_Count);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

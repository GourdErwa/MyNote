package com.gourd.erwa.concurrent.disruptor;

import com.lmax.disruptor.EventHandler;

/**
 * 事件处理者 - 消费者
 *
 * @author lw by 14-7-22.
 */
class DeliveryReportEventHandler implements EventHandler<ValueEvent> {

    private int id;//消费者编号

    DeliveryReportEventHandler(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "DeliveryReportEventHandler{" +
                "id=" + id +
                '}';
    }

    /**
     * @param event      事件
     * @param sequence   事件正在处理
     * @param endOfBatch 是否是最后一个事件在处理
     * @throws Exception Exception
     */
    @Override
    public void onEvent(ValueEvent event, long sequence, boolean endOfBatch) throws Exception {
        Thread.sleep(2000);
        System.out.println(this + "\tevent:\t" + event.getValue()
                + "\tsequence:\t" + sequence
                + "\tendOfBatch:\t" + endOfBatch);
    }
}

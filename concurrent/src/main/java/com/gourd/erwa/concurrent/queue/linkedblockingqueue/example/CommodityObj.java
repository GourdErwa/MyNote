package com.gourd.erwa.concurrent.queue.linkedblockingqueue.example;

import java.util.UUID;

/**
 * 模拟商品对象
 *
 * @author wei.Li by 14-8-21.
 */
class CommodityObj {

    private String objId;

    CommodityObj() {
        this.objId = UUID.randomUUID().toString();
    }

    public String getObjId() {
        return objId;
    }

    public void setObjId(String objId) {
        this.objId = objId;
    }

    @Override
    public String toString() {
        return "Obj{" +
                "objId='" + objId + '\'' +
                '}';
    }
}

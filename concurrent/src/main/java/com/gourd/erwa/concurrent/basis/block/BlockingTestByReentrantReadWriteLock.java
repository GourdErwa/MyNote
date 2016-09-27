package com.gourd.erwa.concurrent.basis.block;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * AtomicBoolean + ReentrantReadWriteLock 实现
 *
 * @author wei.Li
 */
class BlockingTestByReentrantReadWriteLock extends AbsBlockingTest {

    private final AtomicBoolean canInsert = new AtomicBoolean(true);
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    //得到一个可被多个读操作共用的读锁，但它会排斥所有写操作
    private final Lock readLock = lock.readLock();
    //得到一个写锁，它会排斥所有其他的读操作和写操作
    private final Lock writeLock = lock.writeLock();

    @Override
    void resetSettingLogicProcessingHandle() {
        writeLock.lock();
        canInsert.set(false);
        try {
            super.resetSettingLogicProcessing();
        } finally {
            writeLock.unlock();
            canInsert.set(true);
        }
    }

    @Override
    void addEventLogicProcessingHandle(int i) {

        final boolean b = canInsert.get();
        if (b) {
            super.addEventLogicProcessing(i);
        } else {
            this.addEventSynchronized(i);
        }
    }

    private void addEventSynchronized(int i) {
        readLock.lock();
        try {
            super.addEventLogicProcessing(i);
        } finally {
            readLock.unlock();
        }
    }


}

package com.gourd.erwa.concurrent.basis.block;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * AtomicBoolean + ReentrantLock 实现
 *
 * @author wei.Li
 */
class BlockingTestByReentrantLock extends AbsBlockingTest {

    private final AtomicBoolean canInsert = new AtomicBoolean(true);
    private final Lock canInsertLock = new ReentrantLock();

    @Override
    void resetSettingLogicProcessingHandle() {
        canInsertLock.lock();
        canInsert.set(false);
        try {
            super.resetSettingLogicProcessing();
        } finally {
            canInsertLock.unlock();
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
        canInsertLock.lock();
        try {
            super.addEventLogicProcessing(i);
        } finally {
            canInsertLock.unlock();
        }
    }


}

package com.gourd.erwa.concurrent.basis.block;

/**
 * synchronized 实现
 *
 * @author wei.Li
 */
class BlockingTestBySynchronized extends AbsBlockingTest {

    private final Object lock = new Object();

    @Override
    void resetSettingLogicProcessingHandle() {
        synchronized (lock) {
            super.resetSettingLogicProcessing();
        }
    }

    @Override
    void addEventLogicProcessingHandle(int i) {
        synchronized (lock) {
            super.addEventLogicProcessing(i);
        }
    }
}

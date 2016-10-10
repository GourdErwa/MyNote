package com.gourd.erwa.design.observer;

import java.util.Observable;

/**
 * @author wei.Li
 */
class BeingWatched extends Observable {


    void counter(int period) {
        for (; period >= 0; period--) {
            setChanged();
            notifyObservers(period);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("Sleep interrupeted");
            }
        }
    }

}

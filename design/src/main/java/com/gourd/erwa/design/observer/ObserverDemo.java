package com.gourd.erwa.design.observer;

/**
 * @author wei.Li
 */
class ObserverDemo {

    public static void main(String[] args) {

        Watcher watcher = new Watcher();//观察者

        BeingWatched beingWatched = new BeingWatched();//被观察者

        beingWatched.addObserver(watcher);

        int period = 0;
        for (; period >= 0; period--) {
            //beingWatched.setChanged();
            beingWatched.notifyObservers(period);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("Sleep interrupeted");
            }
        }

        beingWatched.notifyObservers();

    }


}

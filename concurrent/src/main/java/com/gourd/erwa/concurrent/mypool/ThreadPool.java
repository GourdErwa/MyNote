package com.gourd.erwa.concurrent.mypool;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by lw on 14-4-22.
 * <p>
 * 线程池
 */
public class ThreadPool {

    // private static boolean debug = taskLogger.isInfoEnabled();

    private static final int SYSTEM_BUSY_TASK_COUNT = 150;
    public static boolean systemIsBusy = false;
    /* 已经处理的任务数 */
    private static int TASKCOUNTER = 0;
    /* 单例 */
    private static ThreadPool instance = ThreadPool.getInstance();
    /* 任务队列 */
    private static List<Task> taskQueue = Collections
            .synchronizedList(new LinkedList<Task>());
    /* 池中的所有线程 */
    public PoolWorker[] workers;
    /* 默认池中线程数 */
    private int WORKER_NUM = 5;

    private ThreadPool() {

        workers = new PoolWorker[WORKER_NUM];
        for (int i = 0; i < workers.length; i++) {
            workers[i] = new PoolWorker(i);
        }
    }

    private ThreadPool(int pool_worker_num) {
        WORKER_NUM = pool_worker_num;
        workers = new PoolWorker[WORKER_NUM];
        for (int i = 0; i < workers.length; i++) {
            workers[i] = new PoolWorker(i);
        }
    }

    public static synchronized ThreadPool getInstance() {
        if (instance == null)
            return new ThreadPool();
        return instance;
    }

    /**
     * 增加新的任务
     * 每增加一个新任务，都要唤醒任务队列
     *
     * @param newTask newTask
     */
    public void addTask(Task newTask) {
        synchronized (taskQueue) {
            newTask.setTaskId(++TASKCOUNTER);
            newTask.setSubmitTime(new Date());
            taskQueue.add(newTask);
            /* 唤醒队列, 开始执行 */
            taskQueue.notifyAll();
        }
    }

    /**
     * 批量增加新任务
     *
     * @param taskes taskes
     */
    public void batchAddTask(Task[] taskes) {
        if (taskes == null || taskes.length == 0) {
            return;
        }
        synchronized (taskQueue) {
            for (int i = 0; i < taskes.length; i++) {
                if (taskes[i] == null) {
                    continue;
                }
                taskes[i].setTaskId(++TASKCOUNTER);
                taskes[i].setSubmitTime(new Date());
                taskQueue.add(taskes[i]);
            }
            /* 唤醒队列, 开始执行 */
            taskQueue.notifyAll();
        }
        for (int i = 0; i < taskes.length; i++) {
            if (taskes[i] == null) {
                continue;
            }
        }
    }

    /**
     * 线程池信息
     *
     * @return 线程池信息
     */
    public String getInfo() {
        StringBuffer sb = new StringBuffer();
        sb.append("\nTask Queue Size:" + taskQueue.size());
        for (int i = 0; i < workers.length; i++) {
            sb.append("\nWorker " + i + " is "
                    + ((workers[i].isWaiting()) ? "Waiting." : "Running."));
        }
        return sb.toString();
    }

    /**
     * 销毁线程池
     */
    public synchronized void destroy() {
        for (int i = 0; i < WORKER_NUM; i++) {
            workers[i].stopWorker();
            workers[i] = null;
        }
        taskQueue.clear();
    }

    /**
     * 池中工作线程
     */
    private class PoolWorker extends Thread {

        private int index = -1;
        /* 该工作线程是否有效 */
        private boolean isRunning = true;
        /* 该工作线程是否可以执行新任务 */
        private boolean isWaiting = true;

        public PoolWorker(int index) {
            this.index = index;
            start();
        }

        public void stopWorker() {
            this.isRunning = false;
        }

        public boolean isWaiting() {
            return this.isWaiting;
        }

        /**
         * 循环执行任务
         * 这也许是线程池的关键所在
         */
        public void run() {
            while (isRunning) {
                Task r = null;
                synchronized (taskQueue) {
                    while (taskQueue.isEmpty()) {
                        try {
                            /* 任务队列为空，则等待有新任务加入从而被唤醒 */
                            taskQueue.wait(20);
                        } catch (InterruptedException ie) {
                        }
                    }
                    /* 取出任务执行 */
                    r = (Task) taskQueue.remove(0);
                }
                System.out.println(this.getName() + "/工作线程..run....");
                if (r != null) {
                    isWaiting = false;
                    try {

                        /* 该任务是否需要立即执行 */
                        if (r.needExecuteImmediate()) {
                            new Thread(r).start();
                        } else {
                            r.run();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    isWaiting = true;
                    r = null;
                }
            }
        }
    }
}

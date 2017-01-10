/*
 * Written by wei.Li and released to the public domain
 * Welcome to correct discussion as explained at
 *
 * -----------------------------------------------------------------
 *
 * GitHub:  https://github.com/GourdErwa
 * CSDN  :	http://blog.csdn.net/xiaohulunb
 * WeiBo :	http://www.weibo.com/xiaohulunb  	@GourdErwa
 * Email :	gourderwa@163.com
 *
 * Personal home page: http://grouderwa.com
 */

package com.gourd.erwa.concurrent.jdkpool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * The type Executor.
 *
 * @author wei.Li by 15/3/18 (gourderwa@163.com).
 */
public class ExecutorUse {

    private Map<String, FutureTask<String>> tasks = new HashMap<>();

    // 构造一个线程
    private ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
    }

    /**
     * Add task list.
     *
     * @param tasksList the tasks list
     */
    public void addTaskList(List<Callable<String>> tasksList) {
        for (Callable<String> t : tasksList) {
            FutureTask<String> futureTask = new FutureTask<String>(t);
            executor.execute(futureTask);

            String key = Long.toHexString(System.nanoTime());
            tasks.put(key, futureTask);
        }
    }

    /**
     * Add task string.
     *
     * @param task the task
     * @return string
     */
    public String addTask(Callable<String> task) {
        FutureTask<String> futureTask = new FutureTask<>(task);
        executor.execute(futureTask);

        String key = Long.toHexString(System.nanoTime());
        tasks.put(key, futureTask);
        return key;
    }

    /**
     * Add db task string.
     *
     * @param task the task
     * @return string
     */
    public String addDBTask(Callable<String> task) {
        FutureTask<String> futureTask = new FutureTask<String>(task) {
            public boolean cancel(boolean mayInterruptIfRunning) {
                System.out.println("Roll Back and Closs Session");
                return super.cancel(mayInterruptIfRunning);
            }
        };
        executor.execute(futureTask);

        String key = Long.toHexString(System.nanoTime());
        tasks.put(key, futureTask);
        return key;
    }

    /**
     * Task is done boolean.
     *
     * @param key the key
     * @return boolean
     */
    public boolean taskIsDone(String key) {
        FutureTask<String> futureTask = tasks.get(key);
        return futureTask != null && futureTask.isDone();
    }

    /**
     * Task is cancelled boolean.
     *
     * @param key the key
     * @return boolean
     */
    public boolean taskIsCancelled(String key) {
        FutureTask<String> futureTask = tasks.get(key);
        return futureTask != null && futureTask.isCancelled();
    }

    /**
     * Gets task result.
     *
     * @param key the key
     * @return task result
     */
    public String getTaskResult(String key) {
        FutureTask<String> futureTask = tasks.get(key);

        if (futureTask.isDone()) {
            try {
                String result = futureTask.get();
                tasks.remove(key);
                return result;
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * Add task and wait result string.
     *
     * @param task the task
     * @return string
     */
    public String addTaskAndWaitResult(Callable<String> task) {
        FutureTask<String> futureTask = new FutureTask<String>(task);
        executor.execute(futureTask);

        String key = Long.toHexString(System.nanoTime());
        tasks.put(key, futureTask);
        try {
            return futureTask.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Remove all task.
     */
    public void removeAllTask() {
        for (String key : tasks.keySet()) {
            executor.remove(tasks.get(key));
            tasks.remove(key);
        }
    }

    /**
     * Remove query task.
     *
     * @param key the key
     */
    public void removeQueryTask(String key) {
        executor.remove(tasks.get(key));
    }

    /**
     * Remove task.
     *
     * @param key the key
     */
    public void removeTask(String key) {
        tasks.remove(key);
    }

    /**
     * Clear task list.
     */
    public void clearTaskList() {
        tasks.clear();
    }

    /**
     * Stop.
     */
    public synchronized void stop() {
        try {
            executor.shutdownNow();
            executor.awaitTermination(1L, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            executor = null;
            tasks.clear();
            tasks = null;
        }
    }

    /**
     * Cancel task.
     *
     * @param key the key
     */
    public void cancelTask(String key) {

        FutureTask<String> futureTask = tasks.get(key);
        if (futureTask != null) {
            if (!futureTask.isDone()) {
                futureTask.cancel(true);
            }
        }
    }

    /**
     * Purge cancel task.
     */
    public void purgeCancelTask() {
        executor.purge();
        executor.getQueue();
    }
}

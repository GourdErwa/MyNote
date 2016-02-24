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

package thread.jdkpool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author wei.Li by 15/3/18 (gourderwa@163.com).
 */
public class Executor_ {

    private Map<String, FutureTask<String>> tasks = new HashMap<>();

    // 构造一个线程
    private ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());

    /**
     * @param args
     */
    public static void main(String[] args) {
    }

    /**
     * @param tasksList
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
     * @param task
     * @return
     */
    public String addTask(Callable<String> task) {
        FutureTask<String> futureTask = new FutureTask<>(task);
        executor.execute(futureTask);

        String key = Long.toHexString(System.nanoTime());
        tasks.put(key, futureTask);
        return key;
    }

    /**
     * @param task
     * @return
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
     * @param key
     * @return
     */
    public boolean taskIsDone(String key) {
        FutureTask<String> futureTask = tasks.get(key);
        if (futureTask != null) {
            return futureTask.isDone();
        }
        return false;
    }

    /**
     * @param key
     * @return
     */
    public boolean taskIsCancelled(String key) {
        FutureTask<String> futureTask = tasks.get(key);
        if (futureTask != null) {
            return futureTask.isCancelled();
        }
        return false;
    }

    /**
     * @param key
     * @return
     */
    public String getTaskResult(String key) {
        FutureTask<String> futureTask = tasks.get(key);

        if (futureTask.isDone()) {
            try {
                String result = futureTask.get();
                tasks.remove(key);
                return result;
            } catch (InterruptedException e) {
                e.printStackTrace();
                return null;
            } catch (ExecutionException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * @param task
     * @return
     */
    public String addTaskAndWaitResult(Callable<String> task) {
        FutureTask<String> futureTask = new FutureTask<String>(task);
        executor.execute(futureTask);

        String key = Long.toHexString(System.nanoTime());
        tasks.put(key, futureTask);
        try {
            return futureTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *
     */
    public void removeAllTask() {
        for (String key : tasks.keySet()) {
            executor.remove((Runnable) tasks.get(key));
            tasks.remove(key);
        }
    }

    /**
     * @param key
     */
    public void removeQueryTask(String key) {
        executor.remove((Runnable) tasks.get(key));
    }

    /**
     * @param key
     */
    public void removeTask(String key) {
        tasks.remove(key);
    }

    /**
     *
     */
    public void clearTaskList() {
        tasks.clear();
    }

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
     * @param key
     */
    public void cancelTask(String key) {

        FutureTask<String> futureTask = tasks.get(key);
        if (futureTask != null) {
            if (!futureTask.isDone()) {
                futureTask.cancel(true);
            }
        }
    }

    public void purgeCancelTask() {
        executor.purge();
        executor.getQueue();
    }
}

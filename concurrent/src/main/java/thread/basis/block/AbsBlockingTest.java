package thread.basis.block;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 刷新配置同时,阻塞添加信息方法执行 (添加的信息使用该配置进行处理)
 *
 * @author wei.Li
 */
public abstract class AbsBlockingTest {

    private static String getTime() {
        return new SimpleDateFormat("mm:ss:SSS").format(new Date());
    }

    public static void main(String[] args) {

        final long blockingTestBySynchronized = testInvoke(new BlockingTestBySynchronized());
        System.out.println("blockingTestBySynchronized = " + blockingTestBySynchronized);

        final long blockingTestByReentrantLock = testInvoke(new BlockingTestByReentrantLock());
        System.out.println("blockingTestByReentrantLock = " + blockingTestByReentrantLock);

        final long blockingTestByReentrantReadWriteLock = testInvoke(new BlockingTestByReentrantReadWriteLock());
        System.out.println("blockingTestByReentrantReadWriteLock = " + blockingTestByReentrantReadWriteLock);
    }

    //return 执行耗时(s)
    private static long testInvoke(AbsBlockingTest test) {

        final List<Callable<Boolean>> tasks = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            int finalI = i;
            tasks.add(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    test.addEventLogicProcessingHandle(finalI);
                    return null;
                }
            });
        }

        for (int i = 0; i < 3; i++) {
            tasks.add(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    test.resetSettingLogicProcessingHandle();
                    return null;
                }
            });
        }
        final ExecutorService executorService = Executors.newCachedThreadPool();
        try {
            final long s = System.currentTimeMillis();
            executorService.invokeAll(tasks);
            return (System.currentTimeMillis() - s) / 1000;
        } catch (InterruptedException ignored) {
            return -1;
        } finally {
            executorService.shutdownNow();
        }

    }

    abstract void resetSettingLogicProcessingHandle();

    void resetSettingLogicProcessing() {
        final String name = Thread.currentThread().getName();
        //刷新配置
        System.out.println(name + "\t" + getTime() + "\t刷新配置开始");
        try {
            Thread.sleep(20000L);
        } catch (InterruptedException ignored) {
        }
        System.out.println(name + "\t" + getTime() + "\t刷新配置结束");
    }

    abstract void addEventLogicProcessingHandle(int i);

    void addEventLogicProcessing(int i) {
        final String name = Thread.currentThread().getName();
        //添加事件进行处理
        System.out.println(name + "\t" + getTime() + "\t添加事件开始" + i);
        try {
            Thread.sleep(3000L);
        } catch (InterruptedException ignored) {
        }
        System.out.println(name + "\t" + getTime() + "\t添加事件结束" + i);
    }

}
/*
pool-1-thread-1	44:58:284	添加事件开始0
pool-1-thread-1	45:01:292	添加事件结束0
pool-1-thread-23	45:01:292	刷新配置开始
pool-1-thread-23	45:21:298	刷新配置结束
pool-1-thread-22	45:21:298	刷新配置开始
pool-1-thread-22	45:41:302	刷新配置结束
pool-1-thread-21	45:41:303	刷新配置开始
pool-1-thread-21	46:01:309	刷新配置结束
pool-1-thread-20	46:01:309	添加事件开始19
pool-1-thread-20	46:04:313	添加事件结束19
pool-1-thread-19	46:04:313	添加事件开始18
pool-1-thread-19	46:07:316	添加事件结束18
pool-1-thread-18	46:07:316	添加事件开始17
pool-1-thread-18	46:10:320	添加事件结束17
pool-1-thread-17	46:10:320	添加事件开始16
pool-1-thread-17	46:13:324	添加事件结束16
pool-1-thread-16	46:13:325	添加事件开始15
pool-1-thread-16	46:16:329	添加事件结束15
pool-1-thread-15	46:16:329	添加事件开始14
pool-1-thread-15	46:19:334	添加事件结束14
pool-1-thread-14	46:19:334	添加事件开始13
pool-1-thread-14	46:22:338	添加事件结束13
pool-1-thread-13	46:22:339	添加事件开始12
pool-1-thread-13	46:25:344	添加事件结束12
pool-1-thread-12	46:25:344	添加事件开始11
pool-1-thread-12	46:28:348	添加事件结束11
pool-1-thread-11	46:28:348	添加事件开始10
pool-1-thread-11	46:31:351	添加事件结束10
pool-1-thread-10	46:31:351	添加事件开始9
pool-1-thread-10	46:34:352	添加事件结束9
pool-1-thread-9	46:34:352	添加事件开始8
pool-1-thread-9	46:37:357	添加事件结束8
pool-1-thread-8	46:37:357	添加事件开始7
pool-1-thread-8	46:40:361	添加事件结束7
pool-1-thread-7	46:40:361	添加事件开始6
pool-1-thread-7	46:43:365	添加事件结束6
pool-1-thread-6	46:43:365	添加事件开始5
pool-1-thread-6	46:46:370	添加事件结束5
pool-1-thread-5	46:46:371	添加事件开始4
pool-1-thread-5	46:49:372	添加事件结束4
pool-1-thread-4	46:49:373	添加事件开始3
pool-1-thread-4	46:52:378	添加事件结束3
pool-1-thread-3	46:52:378	添加事件开始2
pool-1-thread-3	46:55:382	添加事件结束2
pool-1-thread-2	46:55:382	添加事件开始1
pool-1-thread-2	46:58:387	添加事件结束1
blockingTestBySynchronized = 120
pool-2-thread-1	46:58:390	添加事件开始0
pool-2-thread-2	46:58:390	添加事件开始1
pool-2-thread-3	46:58:390	添加事件开始2
pool-2-thread-4	46:58:390	添加事件开始3
pool-2-thread-5	46:58:390	添加事件开始4
pool-2-thread-6	46:58:390	添加事件开始5
pool-2-thread-7	46:58:390	添加事件开始6
pool-2-thread-8	46:58:390	添加事件开始7
pool-2-thread-9	46:58:391	添加事件开始8
pool-2-thread-10	46:58:391	添加事件开始9
pool-2-thread-11	46:58:391	添加事件开始10
pool-2-thread-12	46:58:391	添加事件开始11
pool-2-thread-13	46:58:392	添加事件开始12
pool-2-thread-14	46:58:393	添加事件开始13
pool-2-thread-15	46:58:393	添加事件开始14
pool-2-thread-16	46:58:394	添加事件开始15
pool-2-thread-17	46:58:394	添加事件开始16
pool-2-thread-18	46:58:394	添加事件开始17
pool-2-thread-19	46:58:398	添加事件开始18
pool-2-thread-21	46:58:398	刷新配置开始
pool-2-thread-20	46:58:398	添加事件开始19
pool-2-thread-4	47:01:393	添加事件结束3
pool-2-thread-10	47:01:394	添加事件结束9
pool-2-thread-8	47:01:393	添加事件结束7
pool-2-thread-1	47:01:394	添加事件结束0
pool-2-thread-9	47:01:393	添加事件结束8
pool-2-thread-7	47:01:393	添加事件结束6
pool-2-thread-5	47:01:394	添加事件结束4
pool-2-thread-6	47:01:394	添加事件结束5
pool-2-thread-2	47:01:394	添加事件结束1
pool-2-thread-3	47:01:394	添加事件结束2
pool-2-thread-12	47:01:394	添加事件结束11
pool-2-thread-11	47:01:394	添加事件结束10
pool-2-thread-15	47:01:397	添加事件结束14
pool-2-thread-17	47:01:397	添加事件结束16
pool-2-thread-18	47:01:397	添加事件结束17
pool-2-thread-14	47:01:397	添加事件结束13
pool-2-thread-13	47:01:397	添加事件结束12
pool-2-thread-16	47:01:397	添加事件结束15
pool-2-thread-19	47:01:403	添加事件结束18
pool-2-thread-20	47:01:403	添加事件结束19
pool-2-thread-21	47:18:401	刷新配置结束
pool-2-thread-23	47:18:402	刷新配置开始
pool-2-thread-23	47:38:406	刷新配置结束
pool-2-thread-22	47:38:407	刷新配置开始
pool-2-thread-22	47:58:411	刷新配置结束
blockingTestByReentrantLock = 60
pool-3-thread-1	47:58:416	添加事件开始0
pool-3-thread-2	47:58:416	添加事件开始1
pool-3-thread-3	47:58:416	添加事件开始2
pool-3-thread-4	47:58:416	添加事件开始3
pool-3-thread-5	47:58:416	添加事件开始4
pool-3-thread-6	47:58:416	添加事件开始5
pool-3-thread-7	47:58:416	添加事件开始6
pool-3-thread-8	47:58:417	添加事件开始7
pool-3-thread-9	47:58:417	添加事件开始8
pool-3-thread-10	47:58:417	添加事件开始9
pool-3-thread-11	47:58:417	添加事件开始10
pool-3-thread-12	47:58:417	添加事件开始11
pool-3-thread-13	47:58:417	添加事件开始12
pool-3-thread-14	47:58:417	添加事件开始13
pool-3-thread-15	47:58:417	添加事件开始14
pool-3-thread-16	47:58:417	添加事件开始15
pool-3-thread-17	47:58:418	添加事件开始16
pool-3-thread-18	47:58:418	添加事件开始17
pool-3-thread-19	47:58:418	添加事件开始18
pool-3-thread-20	47:58:418	添加事件开始19
pool-3-thread-21	47:58:418	刷新配置开始
pool-3-thread-3	48:01:418	添加事件结束2
pool-3-thread-1	48:01:419	添加事件结束0
pool-3-thread-2	48:01:418	添加事件结束1
pool-3-thread-5	48:01:419	添加事件结束4
pool-3-thread-6	48:01:419	添加事件结束5
pool-3-thread-7	48:01:420	添加事件结束6
pool-3-thread-8	48:01:420	添加事件结束7
pool-3-thread-9	48:01:420	添加事件结束8
pool-3-thread-10	48:01:420	添加事件结束9
pool-3-thread-12	48:01:420	添加事件结束11
pool-3-thread-11	48:01:420	添加事件结束10
pool-3-thread-13	48:01:420	添加事件结束12
pool-3-thread-15	48:01:420	添加事件结束14
pool-3-thread-17	48:01:420	添加事件结束16
pool-3-thread-4	48:01:419	添加事件结束3
pool-3-thread-20	48:01:421	添加事件结束19
pool-3-thread-19	48:01:421	添加事件结束18
pool-3-thread-18	48:01:420	添加事件结束17
pool-3-thread-16	48:01:420	添加事件结束15
pool-3-thread-14	48:01:420	添加事件结束13
pool-3-thread-21	48:18:424	刷新配置结束
pool-3-thread-22	48:18:424	刷新配置开始
pool-3-thread-22	48:38:429	刷新配置结束
pool-3-thread-23	48:38:429	刷新配置开始
pool-3-thread-23	48:58:432	刷新配置结束
blockingTestByReentrantReadWriteLock = 60
 */

package com.gourd.erwa.concurrent.basis.block;

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
abstract class AbsBlockingTest {

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

    /**
     * @param test 测试实现类
     * @return return 执行耗时(s)
     */
    private static <T extends AbsBlockingTest> long testInvoke(T test) {

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

    /**
     * 刷新配置 - 需要20s 处理时间
     */
    void resetSettingLogicProcessing() {
        final String name = Thread.currentThread().getName();
        System.out.println(name + "\t" + getTime() + "\t刷新配置开始");
        try {
            Thread.sleep(20000L);
        } catch (InterruptedException ignored) {
        }
        System.out.println(name + "\t" + getTime() + "\t刷新配置结束");
    }

    abstract void addEventLogicProcessingHandle(int i);

    /**
     * 添加事件使用配置信息进行处理 需要3s 时间 , 配置刷新时默认进行阻塞等待配置更新
     *
     * @param i i
     */
    void addEventLogicProcessing(int i) {

        final String name = Thread.currentThread().getName();
        System.out.println(name + "\t" + getTime() + "\t添加事件开始" + i);
        try {
            Thread.sleep(3000L);
        } catch (InterruptedException ignored) {
        }
        System.out.println(name + "\t" + getTime() + "\t添加事件结束" + i);
    }

}
/*
pool-1-thread-1	36:55:886	添加事件开始0
pool-1-thread-1	36:58:892	添加事件结束0
pool-1-thread-23	36:58:892	刷新配置开始
pool-1-thread-23	37:18:897	刷新配置结束
pool-1-thread-22	37:18:897	刷新配置开始
pool-1-thread-22	37:38:902	刷新配置结束
pool-1-thread-21	37:38:902	刷新配置开始
pool-1-thread-21	37:58:906	刷新配置结束
pool-1-thread-20	37:58:907	添加事件开始19
pool-1-thread-20	38:01:910	添加事件结束19
pool-1-thread-19	38:01:910	添加事件开始18
pool-1-thread-19	38:04:922	添加事件结束18
pool-1-thread-18	38:04:923	添加事件开始17
pool-1-thread-18	38:07:926	添加事件结束17
pool-1-thread-17	38:07:926	添加事件开始16
pool-1-thread-17	38:10:930	添加事件结束16
pool-1-thread-16	38:10:930	添加事件开始15
pool-1-thread-16	38:13:935	添加事件结束15
pool-1-thread-15	38:13:935	添加事件开始14
pool-1-thread-15	38:16:939	添加事件结束14
pool-1-thread-14	38:16:939	添加事件开始13
pool-1-thread-14	38:19:945	添加事件结束13
pool-1-thread-13	38:19:945	添加事件开始12
pool-1-thread-13	38:22:949	添加事件结束12
pool-1-thread-12	38:22:949	添加事件开始11
pool-1-thread-12	38:25:953	添加事件结束11
pool-1-thread-11	38:25:954	添加事件开始10
pool-1-thread-11	38:28:957	添加事件结束10
pool-1-thread-10	38:28:957	添加事件开始9
pool-1-thread-10	38:31:959	添加事件结束9
pool-1-thread-9	38:31:960	添加事件开始8
pool-1-thread-9	38:34:964	添加事件结束8
pool-1-thread-8	38:34:964	添加事件开始7
pool-1-thread-8	38:37:970	添加事件结束7
pool-1-thread-7	38:37:970	添加事件开始6
pool-1-thread-7	38:40:974	添加事件结束6
pool-1-thread-6	38:40:974	添加事件开始5
pool-1-thread-6	38:43:982	添加事件结束5
pool-1-thread-5	38:43:982	添加事件开始4
pool-1-thread-5	38:46:987	添加事件结束4
pool-1-thread-4	38:46:987	添加事件开始3
pool-1-thread-4	38:49:992	添加事件结束3
pool-1-thread-3	38:49:992	添加事件开始2
pool-1-thread-3	38:52:999	添加事件结束2
pool-1-thread-2	38:52:999	添加事件开始1
pool-1-thread-2	38:56:003	添加事件结束1
blockingTestBySynchronized = 120
pool-2-thread-1	38:56:005	添加事件开始0
pool-2-thread-2	38:56:005	添加事件开始1
pool-2-thread-3	38:56:005	添加事件开始2
pool-2-thread-4	38:56:006	添加事件开始3
pool-2-thread-5	38:56:006	添加事件开始4
pool-2-thread-6	38:56:006	添加事件开始5
pool-2-thread-7	38:56:006	添加事件开始6
pool-2-thread-8	38:56:006	添加事件开始7
pool-2-thread-9	38:56:006	添加事件开始8
pool-2-thread-10	38:56:007	添加事件开始9
pool-2-thread-11	38:56:007	添加事件开始10
pool-2-thread-12	38:56:007	添加事件开始11
pool-2-thread-13	38:56:007	添加事件开始12
pool-2-thread-16	38:56:009	添加事件开始15
pool-2-thread-14	38:56:009	添加事件开始13
pool-2-thread-15	38:56:009	添加事件开始14
pool-2-thread-18	38:56:009	添加事件开始17
pool-2-thread-19	38:56:010	添加事件开始18
pool-2-thread-20	38:56:010	添加事件开始19
pool-2-thread-21	38:56:010	刷新配置开始
pool-2-thread-1	38:59:006	添加事件结束0
pool-2-thread-3	38:59:011	添加事件结束2
pool-2-thread-5	38:59:011	添加事件结束4
pool-2-thread-6	38:59:011	添加事件结束5
pool-2-thread-8	38:59:011	添加事件结束7
pool-2-thread-9	38:59:011	添加事件结束8
pool-2-thread-10	38:59:011	添加事件结束9
pool-2-thread-12	38:59:012	添加事件结束11
pool-2-thread-14	38:59:012	添加事件结束13
pool-2-thread-18	38:59:012	添加事件结束17
pool-2-thread-4	38:59:011	添加事件结束3
pool-2-thread-2	38:59:012	添加事件结束1
pool-2-thread-20	38:59:012	添加事件结束19
pool-2-thread-19	38:59:012	添加事件结束18
pool-2-thread-15	38:59:012	添加事件结束14
pool-2-thread-16	38:59:012	添加事件结束15
pool-2-thread-13	38:59:012	添加事件结束12
pool-2-thread-11	38:59:011	添加事件结束10
pool-2-thread-7	38:59:011	添加事件结束6
pool-2-thread-21	39:16:014	刷新配置结束
pool-2-thread-22	39:16:015	刷新配置开始
pool-2-thread-22	39:36:018	刷新配置结束
pool-2-thread-23	39:36:019	刷新配置开始
pool-2-thread-23	39:56:025	刷新配置结束
pool-2-thread-17	39:56:025	添加事件开始16
pool-2-thread-17	39:59:029	添加事件结束16
blockingTestByReentrantLock = 63
pool-3-thread-1	39:59:031	添加事件开始0
pool-3-thread-2	39:59:031	添加事件开始1
pool-3-thread-3	39:59:031	添加事件开始2
pool-3-thread-4	39:59:032	添加事件开始3
pool-3-thread-5	39:59:032	添加事件开始4
pool-3-thread-6	39:59:032	添加事件开始5
pool-3-thread-7	39:59:032	添加事件开始6
pool-3-thread-8	39:59:033	添加事件开始7
pool-3-thread-9	39:59:033	添加事件开始8
pool-3-thread-10	39:59:033	添加事件开始9
pool-3-thread-11	39:59:033	添加事件开始10
pool-3-thread-12	39:59:034	添加事件开始11
pool-3-thread-13	39:59:034	添加事件开始12
pool-3-thread-14	39:59:034	添加事件开始13
pool-3-thread-15	39:59:034	添加事件开始14
pool-3-thread-16	39:59:035	添加事件开始15
pool-3-thread-17	39:59:035	添加事件开始16
pool-3-thread-18	39:59:035	添加事件开始17
pool-3-thread-19	39:59:035	添加事件开始18
pool-3-thread-20	39:59:036	添加事件开始19
pool-3-thread-21	39:59:036	刷新配置开始
pool-3-thread-1	40:02:033	添加事件结束0
pool-3-thread-5	40:02:033	添加事件结束4
pool-3-thread-4	40:02:033	添加事件结束3
pool-3-thread-2	40:02:033	添加事件结束1
pool-3-thread-8	40:02:033	添加事件结束7
pool-3-thread-7	40:02:033	添加事件结束6
pool-3-thread-3	40:02:033	添加事件结束2
pool-3-thread-6	40:02:033	添加事件结束5
pool-3-thread-10	40:02:034	添加事件结束9
pool-3-thread-9	40:02:033	添加事件结束8
pool-3-thread-11	40:02:034	添加事件结束10
pool-3-thread-12	40:02:034	添加事件结束11
pool-3-thread-13	40:02:034	添加事件结束12
pool-3-thread-14	40:02:039	添加事件结束13
pool-3-thread-15	40:02:040	添加事件结束14
pool-3-thread-19	40:02:040	添加事件结束18
pool-3-thread-20	40:02:040	添加事件结束19
pool-3-thread-18	40:02:040	添加事件结束17
pool-3-thread-17	40:02:040	添加事件结束16
pool-3-thread-16	40:02:040	添加事件结束15
pool-3-thread-21	40:19:040	刷新配置结束
pool-3-thread-22	40:19:041	刷新配置开始
pool-3-thread-22	40:39:044	刷新配置结束
pool-3-thread-23	40:39:045	刷新配置开始
pool-3-thread-23	40:59:051	刷新配置结束
blockingTestByReentrantReadWriteLock = 60

 */

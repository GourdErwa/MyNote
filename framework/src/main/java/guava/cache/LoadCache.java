package guava.cache;

import com.google.common.base.Ticker;
import com.google.common.cache.*;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Guava Cache Example
 *
 * @author wei.Li by 14-8-27.
 */
public class LoadCache {

    private static final org.slf4j.Logger LOGGER
            = LoggerFactory.getLogger(LoadCache.class);

    static LoadingCache loadingCache = null;
    static ExecutorService threadPool = Executors.newCachedThreadPool();
    private static boolean isRunTest = true;

    /**
     * 构建缓存
     */
    private static void builderLoadingCache() {

        loadingCache = CacheBuilder.newBuilder()
                .initialCapacity(100)
                .concurrencyLevel(1)
                //.maximumSize(3000)

                //缓存项在给定时间内没有被读/写访问，则回收。
                // 请注意这种缓存的回收顺序和基于大小回收一样。
                .expireAfterAccess(3, TimeUnit.SECONDS)

                //缓存项在给定时间内没有被写访问（创建或覆盖），则回收。
                // 如果认为缓存数据总是在固定时候后变得陈旧不可用，这种回收方式是可取的。
                //.expireAfterWrite(2, TimeUnit.SECONDS)

                //可以为缓存增加自动定时刷新功能。和expireAfterWrite相反，refreshAfterWrite通过定时刷新可以让缓存项保持可用.
                // 但请注意：缓存项只有在被检索时才会真正刷新（如果CacheLoader.refresh实现为异步，那么检索不会被刷新拖慢）。
                // 因此，如果你在缓存上同时声明expireAfterWrite和refreshAfterWrite，缓存并不会因为刷新盲目地定时重置.
                // 如果缓存项没有被检索，那刷新就不会真的发生,缓存项在过期时间后也变得可以回收。
                .refreshAfterWrite(3, TimeUnit.SECONDS)

                .removalListener(removalListenerBuild())

                .ticker(new Ticker() {
                    @Override
                    public long read() {
                        return 500L;
                    }
                })

                // 使用弱引用存储键。当键没有其它（强或软）引用时，缓存项可以被垃圾回收。
                // 因为垃圾回收仅依赖恒等式（==），使用弱引用键的缓存用==而不是equals比较键。
                //.weakKeys()
                //.weakValues()
                // 使用软引用存储值。软引用只有在响应内存需要时，才按照全局最近最少使用的顺序回收。
                // 考虑到使用软引用的性能影响，我们通常建议使用更有性能预测性的缓存大小限定（基于容量回收）
                // 使用软引用值的缓存同样用==而不是equals比较值。
                //  .softValues()

                //开启状态监控
                .recordStats()

                .build(cacheLoaderBuild());

    }

    /**
     * 缓存项被移除时做一些额外操作。
     * 警告：默认情况下，监听器方法是在移除缓存时同步调用的。
     * 因为缓存的维护和请求响应通常是同时进行的，代价高昂的监听器方法在同步模式下会拖慢正常的缓存请求。
     * 在这种情况下，你可以使用RemovalListeners.asynchronous(RemovalListener, Executor)把监听器装饰为异步操作。
     *
     * @return RemovalListener
     */
    private static RemovalListener removalListenerBuild() {
        return RemovalListeners.asynchronous(new RemovalListener<String, String>() {
            @Override
            public void onRemoval(RemovalNotification<String, String> notification) {
                LOGGER.info("onRemoval ... , notification: <{}>", notification.toString());
            }
        }, Executors.newCachedThreadPool());
    }

    /**
     * 有些键不需要刷新，并且我们希望刷新是异步完成的
     *
     * @return CacheLoader
     */
    private static CacheLoader cacheLoaderBuild() {

        return new CacheLoader<String, String>() {
            /**
             * get 时候如果 key 不存在，load 进缓存
             *
             * @param key key
             * @return key 值对应的 value
             * @throws Exception
             */
            @Override
            public String load(String key) throws Exception {
                // LOGGER.info("load(String key) run ... , key: <{}>", key);
                return getValueForKey(key);
            }

            /**
             * 修改 key 对应的 value 值
             * 重载CacheLoader.reload(K, V)可以扩展刷新时的行为，这个方法允许开发者在计算新值时使用旧的值。
             * @param key 刷新的 key 值
             * @param oldValue 对应的 value 值
             * @return task
             */
            @Override
            public ListenableFuture<String> reload(final String key, String oldValue) {

                if (key.contains("2")) {
                    return Futures.immediateFuture(oldValue);
                } else {
                    // asynchronous!
                    ListenableFutureTask<String> task
                            = ListenableFutureTask.create(new Callable<String>() {
                        public String call() {
                            return oldValue + "reload";
                        }
                    });
                    Executors.newCachedThreadPool().execute(task);
                    return task;
                }
            }

            @Override
            public Map<String, String> loadAll(Iterable<? extends String> keys) throws Exception {
                return super.loadAll(keys);
            }
        };
    }


    /**
     * 由 key 计算对应的 value
     *
     * @param key key
     * @return getValueForKey
     */
    private static String getValueForKey(String key) {
        checkNotNull(key);
        return key + "_" + key;
    }


    /**
     * 获取缓存状态
     */
    private static void getCacheStats() {
        LOGGER.info("loadingCache.stats().toString() is : <{}>"
                , loadingCache.stats());
        LOGGER.info("loadingCache.asMap().toString() is : <{}>"
                , loadingCache.asMap());
    }

    private static void runTest() {

        //启动查询缓存数据线程
        for (int i = 0; i < 4; i++) {
            threadPool.execute(new AddOperationRun());
        }

        //定时器输出缓存状态
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                getCacheStats();
            }
        }, 1000L, 2000L);

        //定时器停止测试任务
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                isRunTest = false;
                threadPool.shutdown();
            }
        }, 1000 * 150L);

    }

    public static void main(String[] args) {
        builderLoadingCache();

        runTest();
    }

    /**
     * 添加测试数据到缓存线程
     * 修改部分缓存的值
     */
    private static class AddOperationRun implements Runnable {

        @Override
        public void run() {
            while (isRunTest) {
                try {
                    for (int i = 0; i < 10; i++) {
                        loadingCache.get(String.valueOf(new Random().nextInt(5)));
                    }
                    //刷新 key 值 , 如果不存在load进缓存，否则进行刷新操作
                    loadingCache.refresh(String.valueOf(new Random().nextInt(10)));
                    Thread.sleep(2000L);
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

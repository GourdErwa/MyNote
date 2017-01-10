package plug.metrics;


import com.codahale.metrics.Counter;
import com.codahale.metrics.MetricRegistry;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

/**
 * Created by lw on 14-7-2.
 * <p>
 * Counter是Gauge的一个特例，维护一个计数器，可以通过inc()和dec()方法对计数器做修改。
 * 使用步骤与Gauge基本类似，在MetricRegistry中提供了静态方法可以直接实例化一个Counter。
 */
public class Counter$ {

    /**
     * 实例化一个counter,同样可以通过如下方式进行实例化再注册进去
     * pendingJobs = new Counter();
     * registry.register(MetricRegistry.name(Metrics_Counter.class, "jobs"), pendingJobs);
     */
    private static final Counter PENDING_JOBS = MetricRegistry$.REGISTRY.counter(MetricRegistry.name("count-", "jobs"));

    private static final Queue<Integer> QUEUE = new LinkedList<>();

    private static void add(Integer integer) {
        PENDING_JOBS.inc();
        QUEUE.offer(integer);
    }

    private static void take() {
        PENDING_JOBS.dec();
        QUEUE.poll();
    }

    public static void main(String[] args) throws InterruptedException {

        MetricRegistry$.fetchConsoleReporter().start(3, TimeUnit.SECONDS);

        int i = 0;
        while (true) {
            add(i++);
            add(i++);
            Thread.sleep(1000);
            take();
            if (i == Integer.MAX_VALUE) {
                break;
            }
        }
    }
}

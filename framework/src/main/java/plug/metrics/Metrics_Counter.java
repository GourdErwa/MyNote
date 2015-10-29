package plug.metrics;


import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Counter;
import com.codahale.metrics.MetricRegistry;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by lw on 14-7-2.
 * <p>
 * Counter是Gauge的一个特例，维护一个计数器，可以通过inc()和dec()方法对计数器做修改。
 * 使用步骤与Gauge基本类似，在MetricRegistry中提供了静态方法可以直接实例化一个Counter。
 */
public class Metrics_Counter {
    /**
     * 实例化一个registry，最核心的一个模块，相当于一个应用程序的metrics系统的容器，维护一个Map
     */
    private static final MetricRegistry registry = new MetricRegistry();

    /**
     * 在控制台上打印输出
     */
    private static ConsoleReporter reporter = ConsoleReporter.forRegistry(registry).build();

    /**
     * 实例化一个counter,同样可以通过如下方式进行实例化再注册进去
     * pendingJobs = new Counter();
     * registry.register(MetricRegistry.name(Metrics_Counter.class, "pending-jobs"), pendingJobs);
     */
    private static Counter pendingJobs = registry.counter(MetricRegistry.name("count-", "pedding-jobs"));

    private static Queue<String> queue = new LinkedList<String>();

    public static void add(String str) {
        pendingJobs.inc();
        queue.offer(str);
    }

    public static String take() {
        pendingJobs.dec();
        return queue.poll();
    }

    public static void main(String[] args) throws InterruptedException {
        Set<String> stringSet = registry.getNames();

        //Starts the reporter polling at the given period.
        reporter.start(3, TimeUnit.SECONDS);
        while (true) {
            add("1");
            Thread.sleep(1000);
            //take();
            //stringSet.forEach(System.out::println);
        }
    }
}

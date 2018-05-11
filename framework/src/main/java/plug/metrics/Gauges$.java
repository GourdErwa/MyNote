package plug.metrics;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Gauge;
import com.codahale.metrics.MetricRegistry;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/**
 * Created by lw on 14-7-2.
 * <p>
 * core包主要提供如下核心功能：
 * <p>
 * Metrics Registries类似一个metrics容器，维护一个Map，可以是一个服务一个实例。
 * 支持五种metric类型：Gauges、Counters、Meters、Histograms和Timers。
 * 可以将metrics值通过JMX、Console，CSV文件和SLF4J loggers发布出来。
 */
public class Gauges$ {
    /**
     * 实例化一个registry，最核心的一个模块，相当于一个应用程序的metrics系统的容器，维护一个Map
     */
    private static final MetricRegistry metrics = new MetricRegistry();

    private static Queue<String> queue = new LinkedBlockingDeque<String>();

    /**
     * 在控制台上打印输出
     */
    private static ConsoleReporter registry = ConsoleReporter.forRegistry(metrics).build();

    /**
     * Gauges是一个最简单的计量，一般用来统计瞬时状态的数据信息，比如系统中处于pending状态的job
     *
     * @param args args
     * @throws InterruptedException InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        //1秒打印一次
        registry.start(1, TimeUnit.SECONDS);

        //实例化一个Gauge
        Gauge<Integer> gauge = new Gauge<Integer>() {
            @Override
            public Integer getValue() {
                return queue.size();
            }
        };

        //注册到容器中
        metrics.register(MetricRegistry.name(Gauges$.class, "pending-job", "size"), gauge);

       /* //测试JMX
        JmxReporter jmxReporter = JmxReporter.forRegistry(metrics).build();
        jmxReporter.start();*/

        //模拟数据
        for (int i = 0; i < 20000; i++) {
            queue.add("a");
            Thread.sleep(1000);
        }
    }

    /**
     * 通过以上步骤将会向MetricsRegistry容器中注册一个名字为com.java.metrics.Metrics_Gauges.pending-job.size的metrics，实时获取队列长度的指标。
     * 另外，Core包种还扩展了几种特定的Gauge：

     JMX Gauges—提供给第三方库只通过JMX将指标暴露出来。
     Ratio Gauges—简单地通过创建一个gauge计算两个数的比值。
     Cached Gauges—对某些计量指标提供缓存
     */
}

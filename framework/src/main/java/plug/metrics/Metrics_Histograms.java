package plug.metrics;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Histogram;
import com.codahale.metrics.MetricRegistry;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by lw on 14-7-2.
 * <p>
 * Histograms主要使用来统计数据的分布情况，最大值、最小值、平均值、中位数，百分比（75%、90%、95%、98%、99%和99.9%）。
 * 例如，需要统计某个页面的请求响应时间分布情况，可以使用该种类型的Metrics进行统计。具体的样例代码如下：
 */
public class Metrics_Histograms {
    /**
     * 实例化一个registry，最核心的一个模块，相当于一个应用程序的metrics系统的容器，维护一个Map
     */
    private static final MetricRegistry registry = new MetricRegistry();
    /**
     * 实例化一个Histograms
     */
    private static final Histogram randomNums = registry.histogram(MetricRegistry.name(Metrics_Histograms.class, "random"));
    /**
     * 在控制台上打印输出
     */
    private static ConsoleReporter reporter = ConsoleReporter.forRegistry(registry).build();

    public static void handleRequest(double random) {
        randomNums.update((int) (random * 100));
    }

    public static void main(String[] args) throws InterruptedException {
        reporter.start(3, TimeUnit.SECONDS);
        Random rand = new Random();
        while (true) {
            handleRequest(rand.nextDouble());
            Thread.sleep(100);
        }
    }
}

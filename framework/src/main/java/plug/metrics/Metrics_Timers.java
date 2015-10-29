package plug.metrics;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by lw on 14-7-2.
 * <p>
 * Timers主要是用来统计某一块代码段的执行时间以及其分布情况，具体是基于Histograms和Meters来实现的。
 */
public class Metrics_Timers {
    /**
     * 实例化一个registry，最核心的一个模块，相当于一个应用程序的metrics系统的容器，维护一个Map
     */
    private static final MetricRegistry metrics = new MetricRegistry();
    /**
     * 实例化一个Meter
     */
//    private static final Timer requests = metrics.timer(name(TestTimers.class, "request"));
    private static final Timer requests = metrics.timer(MetricRegistry.name(Metrics_Timers.class, "request"));
    /**
     * 在控制台上打印输出
     */
    private static ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics).build();

    public static void handleRequest(int sleep) {
        Timer.Context context = requests.time();
        try {
            //some operator
            Thread.sleep(sleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            context.stop();
        }

    }

    public static void main(String[] args) throws InterruptedException {
        reporter.start(3, TimeUnit.SECONDS);
        Random random = new Random();
        while (true) {
            handleRequest(random.nextInt(1000));
        }
    }
}

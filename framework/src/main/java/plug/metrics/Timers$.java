package plug.metrics;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import static plug.metrics.MetricRegistry$.REGISTRY;

/**
 * Created by lw on 14-7-2.
 * <p>
 * Timers主要是用来统计某一块代码段的执行时间以及其分布情况，具体是基于Histograms和Meters来实现的。
 */
public class Timers$ {

    /**
     * 实例化一个Meter
     */
    private static final Timer REQUEST = REGISTRY.timer(MetricRegistry.name(Timers$.class, "request"));

    private static void handleRequest(int sleep) {
        Timer.Context context = REQUEST.time();
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
        MetricRegistry$.fetchConsoleReporter().start(3, TimeUnit.SECONDS);
        Random random = new Random();
        while (true) {
            handleRequest(random.nextInt(1000));
        }
    }
}

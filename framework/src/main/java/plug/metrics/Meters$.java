package plug.metrics;

import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import static plug.metrics.MetricRegistry$.REGISTRY;

/**
 * Created by lw on 14-7-2.
 * <p>
 * Meters用来度量某个时间段的平均处理次数（request per second），每1、5、15分钟的TPS。
 * 比如一个service的请求数，通过metrics.meter()实例化一个Meter之后，然后通过meter.mark()方法就能将本次请求记录下来。
 * 统计结果有总的请求数，平均每秒的请求数，以及最近的1、5、15分钟的平均TPS。
 */
public class Meters$ {

    /**
     * 实例化一个Meter
     */
    private static final Meter REQUESTS = REGISTRY.meter(MetricRegistry.name(Meters$.class, "request"));

    private static void handleRequest() {
        REQUESTS.mark();
    }

    public static void main(String[] args) throws InterruptedException {

        MetricRegistry$.fetchConsoleReporter().start(3, TimeUnit.SECONDS);

        while (true) {
            handleRequest();
            Thread.sleep(new Random().nextInt(3000));
        }
    }

}
